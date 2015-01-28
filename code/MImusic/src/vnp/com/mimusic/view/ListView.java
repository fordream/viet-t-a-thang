package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

//vnp.com.mimusic.view.ListView
public class ListView extends LinearLayout {
	private android.widget.ListView list;
	private TextView message;

	public ListView(Context context) {
		super(context);
		init();
	}

	public ListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list, this);
		list = Conts.getView(this, R.id.list);
		message = Conts.getView(this, R.id.message);
	}

	public void setMessage(String message) {

		if (list.getCount() > 0) {
			message = "";
		}
		
		this.message.setText(message);
	}
}