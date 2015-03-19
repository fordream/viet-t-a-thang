package vnp.com.mimusic.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vnp.com.db.VasContact;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.MoiDvChoNhieuNguoiItemView;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.viettel.vtt.vdealer.R;

public abstract class MoiDvChoNhieuNguoiAdaper extends CursorAdapter {
	private List<String> listSelect = new ArrayList<String>();
	private List<String> listAdd = new ArrayList<String>();

	public List<String> getListSeList() {
		return listSelect;
	}

	public List<String> getListAdd() {
		return listAdd;
	}

	public abstract void addOrRemove(String _id, boolean isAdd, int position);

	public void remove(String _id) {
		if (listSelect.contains(_id)) {
			listSelect.remove(_id);
		} else {
			listAdd.remove(_id);
		}
	}

	public void addSdt(String sdt, Context context) {
		String selection = String.format("%s = '%s'", VasContact.PHONE, sdt);
		Cursor cursor = context.getContentResolver().query(VasContact.CONTENT_URI, null, selection, null, null);
		int position = 0;
		String _id = null;
		String user = null;
		if (cursor != null && cursor.moveToNext()) {
			position = cursor.getPosition();
			if (!listSelect.contains(cursor.getString(cursor.getColumnIndex(VasContact._ID)))) {
				_id = cursor.getString(cursor.getColumnIndex(VasContact._ID));
				user = cursor.getString(cursor.getColumnIndex(VasContact.PHONE));
			} else {
//				Conts.toast(context, mContext.getString(R.string.daaddsdt));
				
				Conts.showDialogDongYCallBack(mContext, mContext.getString(R.string.daaddsdt));
				return;
			}
		}

		if (cursor != null)
			cursor.close();
		if (_id != null) {
			add(_id, user, position);
		} else {
			if (listAdd.contains(sdt)) {
				Conts.showDialogDongYCallBack(mContext, mContext.getString(R.string.daaddsdt));
//				Conts.toast(context, mContext.getString(R.string.daaddsdt));
			} else {
				listAdd.add(sdt);
				addOrRemoveSdt(true, sdt, position);
			}
		}
	}

	public abstract void addOrRemoveSdt(boolean isAdd, String sdt, int position);

	private void add(String _id, String user, int position) {
		map.put(_id, user);
		if (listSelect.contains(_id)) {
			listSelect.remove(_id);
			addOrRemove(_id, false, position);
		} else {
			listSelect.add(_id);
			addOrRemove(_id, true, position);
		}

		notifyDataSetChanged();
	}

	private String service_code;

	public MoiDvChoNhieuNguoiAdaper(Context context, Cursor c, String service_code) {
		super(context, c);
		this.service_code = service_code;
		if (Conts.isBlank(service_code)) {
			this.service_code = "";
		}
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		if (convertView == null) {
			convertView = new MoiDvChoNhieuNguoiItemView(context);
		}

		((MoiDvChoNhieuNguoiItemView) convertView).initData(cursor, textSearch, service_code);

		CheckBox menu_right_detail_checkbox = (CheckBox) ((MoiDvChoNhieuNguoiItemView) convertView).findViewById(R.id.menu_right_detail_checkbox);
		menu_right_detail_checkbox.setOnCheckedChangeListener(null);

		final String _id = cursor.getString(cursor.getColumnIndex(VasContact._ID));
		final String user = cursor.getString(cursor.getColumnIndex(VasContact.PHONE));
		menu_right_detail_checkbox.setChecked(listSelect.contains(_id));
		final int position = cursor.getPosition();
		menu_right_detail_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				add(_id, user, position);
			}
		});

		convertView.findViewById(R.id.menurightitem_main).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				add(_id, user, position);
			}
		});

		((MoiDvChoNhieuNguoiItemView) convertView).updateBackground(cursor.getPosition());

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return new MoiDvChoNhieuNguoiItemView(context);
	}

	private String textSearch = "";

	public void setTextSearch(String textSearh) {
		// Conts.toast(mContext, textSearh);
		this.textSearch = textSearh;
	}

	private Map<String, String> map = new HashMap<String, String>();

	public String getUserFrom_ID(String _id) {
		return map.get(_id);
	}
}