package vnp.com.mimusic.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//vnp.com.mimusic.view.MusicListView
public class MusicListView extends ListView {
	private FooterBangXepHangNoDataView headerListTextView;

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
			// setSelector(R.drawable.xml_list_selection);
			setDividerHeight(0);
			setDivider(null);
		} catch (Exception exception) {

		}
		try {
			setOverScrollMode(android.view.View.OVER_SCROLL_NEVER);
			headerListTextView = new FooterBangXepHangNoDataView(getContext());
			headerListTextView.setText(false, null);
			addFooterView(headerListTextView);
		} catch (Exception exception) {

		}
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
		setAdapter(null);
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

	public void setTextNoDataX(boolean needShow, Spanned fromHtml) {
		headerListTextView.setTextNoDataX(needShow, fromHtml);
		setAdapter(new ArrayAdapter<String>(getContext(), 0, new String[] {}));
	}

	public void setTextNoDataX2(boolean needShow, Spanned fromHtml) {
		headerListTextView.setTextNoDataX2(needShow, fromHtml);
		setAdapter(new ArrayAdapter<String>(getContext(), 0, new String[] {}));
	}

}