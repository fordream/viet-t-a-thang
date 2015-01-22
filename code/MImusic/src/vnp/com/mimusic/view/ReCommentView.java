package vnp.com.mimusic.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * vnp.com.mimusic.view.HeaderView
 * 
 * @author teemo
 * 
 */

public abstract class ReCommentView extends LinearLayout {
	public abstract void addContact(String msisdn, String name);

	public abstract void addDv(String cs);

	private LinearLayout recommnet_list_dv_banchay;
	private JSONObject responseRecommend;

	public ReCommentView(Context context, JSONObject responseRecommend) {
		super(context);
		this.responseRecommend = responseRecommend;
		init();
	}

	public ReCommentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recomment, this);
		TextView recomment_dialog_date = (TextView) findViewById(R.id.recomment_dialog_date);
		Calendar calendar = Calendar.getInstance();
		recomment_dialog_date.setText(String.format("%s/%s/%s", calendar.get(Calendar.YEAR) + "", (1 + calendar.get(Calendar.MONTH)) + "", calendar.get(Calendar.DAY_OF_MONTH)) + "");
		findViewById(R.id.recomment_main_background).setOnClickListener(null);
		recommnet_list_dv_banchay = (LinearLayout) findViewById(R.id.recommnet_list_dv_banchay);

		findViewById(R.id.recomment_main_background).setOnTouchListener(new OnTouchListener() {
			float x = 0, y = 0;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					x = event.getX();
					y = event.getY();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// close();
					if (event.getY() - y < 0) {
						close();
					}
				} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
					if (event.getY() - y < 0) {
						close();
					}
					// close();
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (event.getY() - y >= 0) {
					} else {
						float dy = event.getY() - y;
//						LogUtils.e("DY", dy + "");
						RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) findViewById(R.id.recomment_main).getLayoutParams();
						params.setMargins(0, (int) dy, 0, 0);
						findViewById(R.id.recomment_main).setLayoutParams(params);
						try {
							float f = ((float) Math.abs(dy)) / (float) findViewById(R.id.recomment_main).getHeight();
							findViewById(R.id.recomment_main_background).setAlpha(1 - f);
						} catch (Exception exception) {

						}
					}
				}
				return true;
			}
		});

		findViewById(R.id.recomment_icon_bottom_open).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				start();
			}
		});
		findViewById(R.id.recomment_close).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				close();
			}
		});

		LinearLayout recomment_list = (LinearLayout) findViewById(R.id.recomment_list);

		/**
		 * add list recomment dichvu
		 */
		if (responseRecommend != null) {
			if (responseRecommend.has("data")) {
				try {
					JSONArray array = responseRecommend.getJSONArray("data");
					for (int i = 0; i < array.length(); i++) {
						final JSONObject jsonObject = array.getJSONObject(i);
						RecommentItemDvBanChayView banChayView = new RecommentItemDvBanChayView(getContext());
						banChayView.setBackgroud(i);
						banChayView.setData(jsonObject);
						// DichVu.updateDichvuRecomment(jsonObject,
						// getContext());
						banChayView.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								close();
								addDv(Conts.getString(jsonObject, DichVu.service_code));
							}
						});
						recommnet_list_dv_banchay.addView(banChayView);
					}
				} catch (JSONException e) {
				}

			}
		}

		if (responseRecommend != null) {
			if (responseRecommend.has("data")) {
				List<JSONObject> list = new ArrayList<JSONObject>();
				try {
					JSONArray array = responseRecommend.getJSONArray("data");
					for (int i = 0; i < array.length(); i++) {
						final JSONObject jsonObject = array.getJSONObject(i);

						JSONArray contacts = jsonObject.getJSONArray("contacts");

						for (int index = 0; index < contacts.length(); index++) {
							final JSONObject cotnact = contacts.getJSONObject(index);

							boolean canAdd = true;
							for (JSONObject object : list) {
								if (!Conts.isBlank(Conts.getString(cotnact, "phone")) && Conts.getString(cotnact, "phone").equals(Conts.getString(object, "phone"))) {
									canAdd = false;
								}
							}
							if (canAdd) {
								list.add(cotnact);
							}
						}
					}
				} catch (JSONException e) {
				}

				Comparator<JSONObject> comparator = new Comparator<JSONObject>() {

					@Override
					public int compare(JSONObject lhs, JSONObject rhs) {

						if (lhs != null && rhs != null) {
							String phone1 = Conts.getString(lhs, "name");
							String phone2 = Conts.getString(rhs, "name");

							if (!Conts.isBlank(phone1) && !Conts.isBlank(phone2)) {
								return phone1.compareTo(phone2);
							}
						}
						return 0;
					}
				};

				Collections.sort(list, comparator);
				for (final JSONObject cotnact : list) {
					RecommentItemView recommentItemView = new RecommentItemView(getContext());
					recommentItemView.setData(cotnact);
					recommentItemView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							close();
							addContact(Conts.getString(cotnact, "phone"), Conts.getString(cotnact, "name"));
						}
					});
					recomment_list.addView(recommentItemView);
				}
			}
		}

	}

	private void close() {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_out_top);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				findViewById(R.id.recomment_main_background).setVisibility(View.GONE);
				findViewById(R.id.recomment_main).setVisibility(View.GONE);

				mCLose();
			}
		});

		findViewById(R.id.recomment_main).startAnimation(animation);
	}

	public void mCLose() {

	}

	public void start() {
		findViewById(R.id.recomment_main_background).setVisibility(View.VISIBLE);
		findViewById(R.id.recomment_main_background).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_alpha_in));
		findViewById(R.id.recomment_main).setVisibility(View.VISIBLE);
		findViewById(R.id.recomment_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_top));
	}
}