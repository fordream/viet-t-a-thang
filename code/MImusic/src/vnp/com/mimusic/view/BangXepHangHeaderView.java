package vnp.com.mimusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.viettel.vtt.vdealer.R;

/**
 * vnp.com.mimusic.view.HeaderView
 * 
 * @author teemo
 * 
 */

// vnp.com.mimusic.view.BangXepHangHeaderView
public class BangXepHangHeaderView extends LinearLayout {

	public BangXepHangHeaderView(Context context) {
		super(context);
		init();
	}

	public BangXepHangHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private RadioButton radio0;
	private RadioButton radio1;

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bangxephang_header, this);
		radio0 = (RadioButton) findViewById(R.id.radio0);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		radio0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				int next = radio0.isChecked() ? 0 : 1;
				callNext(next);
			}
		});
	}

	protected void callNext(int next) {
		if (next != index) {
			findViewById(R.id.bangxephang_header_main).post(new NextRunable(next));
		}
	}

	private class NextRunable implements Runnable {
		int next;

		public NextRunable(int next) {
			this.next = next;
		}

		@Override
		public void run() {
			int width = findViewById(R.id.bangxephang_header_main).getWidth();
			TranslateAnimation animation = new TranslateAnimation(width / 2 * index, width / 2 * next, 0, 0);
			animation.setDuration(300);
			animation.setFillAfter(true);
			findViewById(R.id.bangxephang_header_item).startAnimation(animation);
			index = next;

			if (bangXepHangHeaderInterface != null) {
				bangXepHangHeaderInterface.callSoLuong(radio0.isChecked());
			}
		}
	}

	private BangXepHangHeaderInterface bangXepHangHeaderInterface;

	public void setBangXepHangHeaderInterface(BangXepHangHeaderInterface bangXepHangHeaderInterface) {
		this.bangXepHangHeaderInterface = bangXepHangHeaderInterface;
	}

	public interface BangXepHangHeaderInterface {
		public void callSoLuong(boolean isSoluong);
	}

	private int index = 0;

	public void disable() {
		radio1.setClickable(false);
		radio0.setClickable(false);
	}

	public void setDoanhThu(boolean isDoanhThu) {
		if (isDoanhThu) {
			radio1.setChecked(true);
		} else {
			radio0.setChecked(true);
		}
	}

}
