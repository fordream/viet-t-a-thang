package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ListView;

//vnp.com.mimusic.view.MusicListView
public class MusicListView extends ListView {

	public MusicListView(Context context) {
		super(context);
		init();
	}

	public MusicListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MusicListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		try {
			setCacheColorHint(Color.TRANSPARENT);
			setSelector(R.drawable.xml_list_selection);
		} catch (Exception exception) {

		}
	}
}