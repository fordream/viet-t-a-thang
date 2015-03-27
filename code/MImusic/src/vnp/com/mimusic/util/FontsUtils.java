package vnp.com.mimusic.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontsUtils {
	private static FontsUtils utils = new FontsUtils();
	private Context context;
	private Map<String, Typeface> map = new HashMap<String, Typeface>();

	public static FontsUtils getInstance() {
		if (utils == null) {
			utils = new FontsUtils();
		}

		return utils;
	}

	public static final String TOBOTO_LIGHT = "Roboto-Light.ttf";
	public static final String TOBOTO_Regular = "Roboto-Regular.ttf";
	public static final String TOBOTO_Medium = "Roboto-Medium.ttf";
	public static final String TOBOTO_Thin = "Roboto-Thin.ttf";
	private static final String TOBOTO_Bold = "Roboto-Bold.ttf";

	public void setTextFontsRobotoLight(TextView textView) {
		if (map.containsKey(TOBOTO_LIGHT))
			textView.setTypeface(map.get(TOBOTO_LIGHT));
	}

	public void setTextFontsRobotoRegular(TextView textView) {
		if (map.containsKey(TOBOTO_Regular))
			textView.setTypeface(map.get(TOBOTO_Regular));
	}

	public void setTextFontsRobotoMedium(TextView textView) {
		if (map.containsKey(TOBOTO_Medium))
			textView.setTypeface(map.get(TOBOTO_Medium));
	}

	public void setTextFontsRobotoThin(TextView textView) {
		if (map.containsKey(TOBOTO_Thin))
			textView.setTypeface(map.get(TOBOTO_Thin));
	}

	public void setTextFontsRobotoBold(TextView textView) {
		if (map.containsKey(TOBOTO_Bold))
			textView.setTypeface(map.get(TOBOTO_Bold));

	}

	public void init(Context context) {
		if (this.context == null) {
			this.context = context;
		}

		addFont(TOBOTO_LIGHT);
		addFont(TOBOTO_Regular);
		addFont(TOBOTO_Medium);
		addFont(TOBOTO_Thin);
		addFont(TOBOTO_Bold);
	}

	private void addFont(String fontName) {
		if (!map.containsKey(fontName)) {
			Typeface typeface = setTypefaceFromAsset(String.format("fonts/%s", fontName));
			if (typeface != null) {
				map.put(fontName, typeface);
			}
		}
	}

	private FontsUtils() {

	}

	public Typeface setTypefaceFromAsset(String fileAsset) {
		try {
			AssetManager assertManager = context.getAssets();
			Typeface tf = Typeface.createFromAsset(assertManager, fileAsset);
			return tf;
		} catch (Exception e) {
			return null;
		}
	}

	public static void setDefaultFont(Context context, String staticTypefaceFieldName, String fontAssetName) {
		final Typeface regular = Typeface.createFromAsset(context.getAssets(), fontAssetName);
		replaceFont(staticTypefaceFieldName, regular);
	}

	protected static void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
		try {
			final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
			staticField.setAccessible(true);
			staticField.set(null, newTypeface);
		} catch (Exception e) {
		}
	}
}
