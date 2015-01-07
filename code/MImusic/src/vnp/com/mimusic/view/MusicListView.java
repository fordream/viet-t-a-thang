package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//vnp.com.mimusic.view.MusicListView
public class MusicListView extends ListView {
	private HeaderListTextView headerListTextView;

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
			setDividerHeight(0);
			setDivider(null);
		} catch (Exception exception) {

		}
		headerListTextView = new HeaderListTextView(getContext());
		headerListTextView.setText(false, null);
		addFooterView(headerListTextView);
	}

	/**
	 * 
	 * @param needShow
	 * @param message
	 */
	public void setText(boolean needShow, String message) {
		headerListTextView.setText(needShow, message);
	}

	/**
	 * 
	 * @param needShow
	 * @param message
	 */
	public void setText(boolean needShow, int message) {
		headerListTextView.setText(needShow, message);
	}

	/**
	 * 
	 * @param needShow
	 * @param message
	 */
	public void setTextNoData(boolean needShow, String message) {
		headerListTextView.setText(needShow, message);
		setAdapter(new ArrayAdapter<String>(getContext(), 0, new String[] {}));
	}

	/**
	 * 
	 * @param needShow
	 * @param message
	 */
	public void setTextNoData(boolean needShow, int message) {
		headerListTextView.setText(needShow, message);
		setAdapter(new ArrayAdapter<String>(getContext(), 0, new String[] {}));
	}
}