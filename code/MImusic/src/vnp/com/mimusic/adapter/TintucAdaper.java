//package vnp.com.mimusic.adapter;
//
//import vnp.com.mimusic.R;
//import vnp.com.mimusic.view.TintucItemView;
//import android.content.Context;
//import android.database.Cursor;
//import android.support.v4.widget.CursorAdapter;
//import android.view.View;
//import android.view.ViewGroup;
//
//public class TintucAdaper extends CursorAdapter {
//	// private JSONArray jsonArray;
//	public TintucAdaper(Context context, Cursor c) {
//		super(context, c, true);
//	}
//
//	// public TintucAdaper(Context context, JSONArray jsonArray) {
//	// super(context, 0, new JSONObject[] {});
//	// this.jsonArray = jsonArray;
//	//
//	// if (jsonArray.length() == 0) {
//	// // JSONObject jsonObject = new JSONObject();
//	// // "id":"12454",
//	// // "title":"Tiêu đề tin tức",
//	// // "header":"Nội dung tóm tắt",
//	// // "image":"/image.png",
//	// // "public_time":"12h12 12/12/2014",
//	// // "type":1
//	// // try {
//	// // jsonObject.put("id", "12454");
//	// // jsonObject.put("title", "Tiêu đề tin tức");
//	 // jsonObject.put("header", "Nội dung tóm tắt");
//	// // jsonObject.put("image", "/image.png");
//	// // jsonObject.put("public_time", "12h12 12/12/2014");
//	// // jsonObject.put("type", "1");
//	// // } catch (JSONException e) {
//	// // }
//	//
//	// // jsonArray.put(jsonObject);
//	// }
//	// }
//
//	// @Override
//	// public int getCount() {
//	// return jsonArray.length();
//	// }
//
//	// @Override
//	// public JSONObject getItem(int position) {
//	// try {
//	// return jsonArray.getJSONObject(position);
//	// } catch (JSONException e) {
//	// }
//	//
//	// return null;
//	// }
//
//	@Override
//	public View newView(Context context, Cursor cursor, ViewGroup parent) {
//		return new TintucItemView(context);
//	}
//
//	@Override
//	public void bindView(View convertView, Context context, Cursor cursor) {
//		if (convertView == null) {
//			convertView = new TintucItemView(context);
//		}
//		int position = cursor.getPosition();
//		convertView.findViewById(R.id.tintuc_item_main).setBackgroundResource(position % 2 == 0 ? R.drawable.new_tintuc_bg1 : R.drawable.new_tintuc_bg2);
//
//		((TintucItemView) convertView).setData(cursor);
//
//	}
//
//	// @Override
//	// public View getView(int position, View convertView, ViewGroup parent) {
//	//
//	// if (convertView == null) {
//	// convertView = new TintucItemView(parent.getContext());
//	// }
//	//
//	// convertView.findViewById(R.id.tintuc_item_main).setBackgroundResource(position
//	// % 2 == 0 ? R.drawable.new_tintuc_bg1 : R.drawable.new_tintuc_bg2);
//	//
//	// ((TintucItemView) convertView).setData((Cursor)getItem(position));
//	// return convertView;
//	// }
//
//}
