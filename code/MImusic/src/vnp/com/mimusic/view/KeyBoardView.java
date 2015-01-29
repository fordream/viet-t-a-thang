package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * vnp.com.mimusic.view.HeaderView
 * 
 * @author teemo
 * 
 */
public class KeyBoardView extends LinearLayout implements View.OnClickListener {
	private EditText keyboard_number;
	private View keyboard_add;

	public EditText getKeEditText() {
		return keyboard_number;
	}

	public KeyBoardView(Context context) {
		super(context);
		init();
	}

	public KeyBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.keyboard, this);
		findViewById(R.id.keyboard_x).setOnClickListener(this);
		keyboard_add = Conts.getView(this, R.id.keyboard_add);
		keyboard_number = Conts.getView(this, R.id.keyboard_number);
	}

	@Override
	public void onClick(View v) {
		Conts.setTextView(keyboard_number, "");
	}

	public void addActionOnClickAdd(OnClickListener moidichvuchonhieunguoi_add_plusOnCLick) {
		keyboard_add.setOnClickListener(moidichvuchonhieunguoi_add_plusOnCLick);
	}

	public String getString() {
		return keyboard_number.getText().toString().trim();
	}

	public void clear() {
		Conts.setTextView(keyboard_number, "");
	}
}
