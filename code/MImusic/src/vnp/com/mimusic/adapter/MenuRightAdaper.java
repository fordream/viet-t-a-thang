package vnp.com.mimusic.adapter;

import java.util.List;

import vnp.com.mimusic.R;
import vnp.com.mimusic.view.MenuRightItemView;
import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public abstract class MenuRightAdaper extends ArrayAdapter<ContentValues> {
	private List<ContentValues> objects;

	public MenuRightAdaper(Context context, List<ContentValues> objects) {
		super(context, 0, objects);
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new MenuRightItemView(parent.getContext());
		}

		final ContentValues contentValues = objects.get(position);
		((MenuRightItemView) convertView).initData(contentValues);
		return convertView;
	}

	private class OnClickBtnMoi implements OnClickListener {
		private ContentValues contentValues;

		public OnClickBtnMoi(ContentValues contentValues) {
			this.contentValues = contentValues;
		}

		@Override
		public void onClick(View v) {
			openMoi(contentValues);
		}
	}

	public abstract void openMoi(ContentValues contentValues);
}
