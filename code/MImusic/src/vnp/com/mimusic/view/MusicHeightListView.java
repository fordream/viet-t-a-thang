package vnp.com.mimusic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

//vnp.com.mimusic.view.MusicHeightListView
public class MusicHeightListView extends MusicListView {
	private android.view.ViewGroup.LayoutParams params;
	private int old_count = 0;

	public MusicHeightListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MusicHeightListView(Context context) {
		super(context);
	}

	public MusicHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (getCount() != old_count) {
			old_count = getCount();
			params = getLayoutParams();
			params.height = (getCount() - 1) * (old_count > 0 ? getChildAt(0).getHeight() : 0);
			setLayoutParams(params);
		}

		super.onDraw(canvas);
	}
}
