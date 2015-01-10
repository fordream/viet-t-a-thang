package vnp.com.mimusic.base.diablog;

import java.util.Calendar;
import java.util.StringTokenizer;

import vnp.com.mimusic.R;
import vnp.com.mimusic.base.BaseAdialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.vnp.core.view.wheel.WheelView;
import com.vnp.core.view.wheel.WheelView.OnWheelChangedListener;
import com.vnp.core.view.wheel.WheelViewAdapter;

public abstract class LSBHDateDialog extends BaseAdialog implements android.view.View.OnClickListener {
	private int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public LSBHDateDialog(Context context, int theme) {
		super(context, theme);
	}

	public LSBHDateDialog(Context context) {
		super(context);
	}

	private String date;

	public LSBHDateDialog(Context context, String date) {
		super(context);
		this.date = date;
	}

	private WheelView date_wheelview_year, date_wheelview_month, date_wheelview_date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		date_wheelview_year = (WheelView) findViewById(R.id.date_wheelview_year);
		date_wheelview_month = (WheelView) findViewById(R.id.date_wheelview_month);
		date_wheelview_date = (WheelView) findViewById(R.id.date_wheelview_date);

		WheelViewAdapter adapterWheel = new WheelViewAdapter() {

			@Override
			public void unregisterDataSetObserver(DataSetObserver observer) {

			}

			@Override
			public void registerDataSetObserver(DataSetObserver observer) {

			}

			@Override
			public int getItemsCount() {
				return 100;
			}

			@Override
			public View getItem(int index, View convertView, ViewGroup parent) {

				if (convertView == null) {
					convertView = new TextView(parent.getContext());
				}

				LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, (int) parent.getContext().getResources().getDimension(R.dimen.dimen_50dp));
				convertView.setLayoutParams(layoutParams);

				((TextView) convertView).setText((getYear() - 99 + index) + "");
				((TextView) convertView).setGravity(Gravity.CENTER);
				((TextView) convertView).setTextSize(parent.getContext().getResources().getDimension(R.dimen.dimen_13dp));
				((TextView) convertView).setTextColor(Color.WHITE);

				return convertView;
			}

			@Override
			public View getEmptyItem(View convertView, ViewGroup parent) {
				return null;
			}
		};

		date_wheelview_year.setViewAdapter(adapterWheel);

		date_wheelview_month.setViewAdapter(new WheelViewAdapter() {

			@Override
			public void unregisterDataSetObserver(DataSetObserver observer) {

			}

			@Override
			public void registerDataSetObserver(DataSetObserver observer) {

			}

			@Override
			public int getItemsCount() {
				return 12;
			}

			@Override
			public View getItem(int index, View convertView, ViewGroup parent) {

				if (convertView == null) {
					convertView = new TextView(parent.getContext());
				}

				LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, (int) parent.getContext().getResources().getDimension(R.dimen.dimen_50dp));
				convertView.setLayoutParams(layoutParams);
				((TextView) convertView).setText((index + 1) + "");
				((TextView) convertView).setGravity(Gravity.CENTER);
				((TextView) convertView).setTextSize(parent.getContext().getResources().getDimension(R.dimen.dimen_13dp));
				((TextView) convertView).setTextColor(Color.WHITE);

				return convertView;
			}

			@Override
			public View getEmptyItem(View convertView, ViewGroup parent) {
				return null;
			}
		});
		showDate(1, getYear());
		findViewById(R.id.date_dialog_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_bottom));
		findViewById(R.id.date_close).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mDismiss(false);
			}
		});

		findViewById(R.id.date_done).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mDismiss(true);
			}
		});
		try {
			if (!Conts.isBlank(date)) {
				LogUtils.e("date", date);
				StringTokenizer stringTokenizer = new StringTokenizer(date, "/");
				String day = stringTokenizer.nextToken();
				String month = stringTokenizer.nextToken();
				String year = stringTokenizer.nextToken();

				date_wheelview_month.setCurrentItem(Integer.parseInt(month) - 1);
				date_wheelview_date.setCurrentItem(Integer.parseInt(day) - 1);
				date_wheelview_year.setCurrentItem(-getYear() + 99 + Integer.parseInt(year));
			}
		} catch (Exception exception) {

		}
		date_wheelview_month.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				showDate(date_wheelview_month.getCurrentItem() + 1, getYear() - 99 + date_wheelview_year.getCurrentItem());
			}
		});

		date_wheelview_year.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				showDate(date_wheelview_month.getCurrentItem() + 1, getYear() - 99 + date_wheelview_year.getCurrentItem());
			}
		});

	}

	private void showDate(int month, int year) {
		LogUtils.e("data-", year + "");
		int curent = date_wheelview_date.getCurrentItem();
		DateWheelViewAdapter dateWheelViewAdapter = new DateWheelViewAdapter(month, year);
		date_wheelview_date.setViewAdapter(dateWheelViewAdapter);

		if (curent >= dateWheelViewAdapter.getItemsCount()) {
			date_wheelview_date.setCurrentItem(dateWheelViewAdapter.getItemsCount() - 1);
		}
	}

	public void mDismiss(final boolean isSendData) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_out_bottom);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				LSBHDateDialog.this.dismiss();
				if (isSendData) {
					sendData(((date_wheelview_date.getCurrentItem() + 1) < 10 ? "0" : "") + (date_wheelview_date.getCurrentItem() + 1) + "", ((date_wheelview_month.getCurrentItem() + 1) < 10 ? "0"
							: "") + (date_wheelview_month.getCurrentItem() + 1) + "", (getYear() - 99 + date_wheelview_year.getCurrentItem()) + "");
				}
			}
		});
		findViewById(R.id.date_dialog_main).startAnimation(animation);

	}

	public abstract void sendData(String date, String month, String year);

	@Override
	public int getLayout() {
		return R.layout.lsbhdate_dialog;
	}

	@Override
	public void onClick(View v) {
	}

	private class DateWheelViewAdapter implements WheelViewAdapter {
		private int month = 1;
		private int year;

		public DateWheelViewAdapter(int month, int year) {
			this.year = year;
			this.month = month;
		}

		@Override
		public int getItemsCount() {
			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				return 31;
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				return 30;
			}

			return year % 4 == 0 ? 29 : 28;
		}

		@Override
		public View getItem(int index, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new TextView(parent.getContext());
			}

			LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, (int) parent.getContext().getResources().getDimension(R.dimen.dimen_50dp));
			convertView.setLayoutParams(layoutParams);
			((TextView) convertView).setText((index + 1) + "");
			((TextView) convertView).setGravity(Gravity.CENTER);
			((TextView) convertView).setTextSize(parent.getContext().getResources().getDimension(R.dimen.dimen_13dp));
			((TextView) convertView).setTextColor(Color.WHITE);

			return convertView;
		}

		@Override
		public View getEmptyItem(View convertView, ViewGroup parent) {
			return null;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {

		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {

		}

	}

}