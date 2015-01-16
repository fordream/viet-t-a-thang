package vnp.com.mimusic.base.diablog;

import vnp.com.mimusic.R;
import vnp.com.mimusic.base.BaseAdialog;
import vnp.com.mimusic.util.LogUtils;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
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
import com.vnp.core.view.wheel.WheelView.OnWheelClickedListener;
import com.vnp.core.view.wheel.WheelView.OnWheelScrollListener;
import com.vnp.core.view.wheel.WheelViewAdapter;

public abstract class TrangThaiDialog extends BaseAdialog implements android.view.View.OnClickListener {

	private int index = 0;

	public TrangThaiDialog(Context context, int date) {
		super(context);
		this.index = date;
	}

	private WheelView date_wheelview_year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		date_wheelview_year = (WheelView) findViewById(R.id.date_wheelview_year);

		WheelViewAdapter adapterWheel = new WheelViewAdapter() {

			@Override
			public void unregisterDataSetObserver(DataSetObserver observer) {

			}

			@Override
			public void registerDataSetObserver(DataSetObserver observer) {

			}

			@Override
			public int getItemsCount() {
				return 3;
			}

			@Override
			public View getItem(int index, View convertView, ViewGroup parent) {

				if (convertView == null) {
					convertView = new TextView(parent.getContext());
				}

				LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, (int) parent.getContext().getResources().getDimension(R.dimen.dimen_50dp));
				convertView.setLayoutParams(layoutParams);

				String[] array = parent.getResources().getStringArray(R.array.ltrangthai);
				((TextView) convertView).setText(array[index]);
				((TextView) convertView).setGravity(Gravity.CENTER);
				((TextView) convertView).setTextSize(parent.getContext().getResources().getDimension(R.dimen.dimen_13dp));
				((TextView) convertView).setTextColor(Color.BLACK);

				return convertView;
			}

			@Override
			public View getEmptyItem(View convertView, ViewGroup parent) {
				return null;
			}
		};

		date_wheelview_year.setViewAdapter(adapterWheel);
		date_wheelview_year.setCurrentItem(index);

		findViewById(R.id.date_dialog_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_bottom));
		findViewById(R.id.date_close).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mDismiss(false);
			}
		});
		findViewById(R.id.date_done).setVisibility(View.GONE);
		findViewById(R.id.date_done).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mDismiss(true);
			}
		});

		// date_wheelview_year.addChangingListener(new OnWheelChangedListener()
		// {
		// @Override
		// public void onChanged(WheelView wheel, int oldValue, int newValue) {
		//
		// }
		// });

		// date_wheelview_year.addClickingListener(new OnWheelClickedListener()
		// {
		// @Override
		// public void onItemClicked(WheelView wheel, int itemIndex) {
		// LogUtils.e("AAAAA", itemIndex + "");
		// }
		// });
		date_wheelview_year.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// LogUtils.e("AAAAA", wheel.getCurrentItem() + "");
				mDismiss(true);
			}
		});

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
				TrangThaiDialog.this.dismiss();
				if (isSendData)
					sendData(date_wheelview_year.getCurrentItem());
			}
		});
		findViewById(R.id.date_dialog_main).startAnimation(animation);

	}

	public abstract void sendData(int index);

	@Override
	public int getLayout() {
		return R.layout.trangthai_dialog;
	}

	@Override
	public void onClick(View v) {
	}

}