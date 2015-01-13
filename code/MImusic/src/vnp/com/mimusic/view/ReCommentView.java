package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
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
import android.widget.Toast;

/**
 * vnp.com.mimusic.view.HeaderView
 * 
 * @author teemo
 * 
 */

public abstract class ReCommentView extends LinearLayout {
	public abstract void addContact();

	public abstract void addDv();

	private LinearLayout recommnet_list_dv_banchay;

	public ReCommentView(Context context) {
		super(context);
		init();
	}

	public ReCommentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recomment, this);

		findViewById(R.id.recomment_main_background).setOnClickListener(null);
		recommnet_list_dv_banchay = (LinearLayout) findViewById(R.id.recommnet_list_dv_banchay);

		// findViewById(R.id.recomment_icon_bottom).setOnClickListener(new
		// View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// close();
		// }
		// });

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
						float dx = event.getY() - y;
						LogUtils.e("DY", dx + "");
						RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) findViewById(R.id.recomment_main).getLayoutParams();
						params.setMargins(0, (int) dx, 0, 0);
						findViewById(R.id.recomment_main).setLayoutParams(params);
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
		for (int i = 0; i < 5; i++) {
			RecommentItemView recommentItemView = new RecommentItemView(getContext());
			recommentItemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					close();
					addContact();
				}
			});

			recomment_list.addView(recommentItemView);
			// recommentItemView.setOnClickListener(new RecommentItemOnClick());
		}

		for (int i = 0; i < 5; i++) {
			// add
			RecommentItemDvBanChayView banChayView = new RecommentItemDvBanChayView(getContext());

			banChayView.setBackgroud(i);
			banChayView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					close();
					addDv();
				}
			});
			recommnet_list_dv_banchay.addView(banChayView);
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
		findViewById(R.id.recomment_main).setVisibility(View.VISIBLE);
		findViewById(R.id.recomment_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_top));
	}
}