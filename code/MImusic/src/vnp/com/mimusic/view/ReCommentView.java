package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * vnp.com.mimusic.view.HeaderView
 * 
 * @author teemo
 * 
 */

public class ReCommentView extends LinearLayout {

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

		findViewById(R.id.recomment_icon_bottom).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				close();
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
		for (int i = 0; i < 4; i++) {
			RecommentItemView recommentItemView = new RecommentItemView(getContext());
			recomment_list.addView(recommentItemView);
			recommentItemView.setOnClickListener(new RecommentItemOnClick());
		}
	}

	private class RecommentItemOnClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
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
			}
		});
		findViewById(R.id.recomment_main).startAnimation(animation);
	}

	public void start() {
		findViewById(R.id.recomment_main_background).setVisibility(View.VISIBLE);
		findViewById(R.id.recomment_main).setVisibility(View.VISIBLE);
		findViewById(R.id.recomment_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_top));
	}
}