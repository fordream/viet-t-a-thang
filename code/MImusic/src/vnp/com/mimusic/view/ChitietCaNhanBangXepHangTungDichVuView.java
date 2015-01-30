package vnp.com.mimusic.view;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.base.VTAnimationListener;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

//vnp.com.mimusic.view.ChitietCaNhanBangXepHangTungDichVuView
public class ChitietCaNhanBangXepHangTungDichVuView extends LinearLayout implements View.OnClickListener {
	private TextView soGDTrongThang, soGD, soHHTrongThang, soHH, bangxephangItemTvStt;
	private LoadingView loadingView;
	private BangXepHangItemView bXHItemView;
	private vnp.com.mimusic.view.BangXepHangHeaderView bxhHeader;

	public ChitietCaNhanBangXepHangTungDichVuView(Context context) {
		super(context);
		init();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.mainx) {
			// gone();
		}
	}

	@Override
	public void setVisibility(int visibility) {

		super.setVisibility(visibility);

		if (visibility == VISIBLE) {
			findViewById(R.id.mainx).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_scale_in));
		}
	}

	public void gone() {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.abc_scale_out);
		animation.setAnimationListener(new VTAnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				super.onAnimationEnd(animation);
				setVisibility(GONE);
			}
		});

		findViewById(R.id.mainx).startAnimation(animation);
		indexApi++;
	}

	public ChitietCaNhanBangXepHangTungDichVuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chitietcanhanbangxephangtungdichvuview, this);

		findViewById(R.id.mainx).setOnClickListener(this);

		loadingView = Conts.getView(this, R.id.loadingView1);

		bXHItemView = (BangXepHangItemView) findViewById(R.id.bangxephangitemview);
		bangxephangItemTvStt = (TextView) bXHItemView.findViewById(R.id.bangxephang_item_tv_stt);
		bxhHeader = Conts.getView(this, R.id.bxhheader);
		bxhHeader.disable();
		// View blockLayout = bXHItemView.findViewById(R.id.bangxephang_block);
		// blockLayout.setVisibility(View.GONE);

		soGDTrongThang = (TextView) findViewById(R.id.soGDTrongThang);
		soGD = (TextView) findViewById(R.id.soGD);
		soHHTrongThang = (TextView) findViewById(R.id.soHHTrongThang);
		soHH = (TextView) findViewById(R.id.soHH);
	}

	public void initData(Bundle bundle) {
		soGDTrongThang.setText("");
		soGD.setText("");
		soHHTrongThang.setText("");
		soHH.setText("");

		bangxephangItemTvStt.setText(bundle.getString("position"));
		soGD.setText(bundle.getString("quantity"));
		soHH.setText(String.format(getContext().getString(R.string.format_tien), bundle.getString("commission")));
		bXHItemView.setType(type);
		bXHItemView.setDataBundle(bundle);

		callApi(bundle);
	}

	private int indexApi = 0;

	private void callApi(Bundle arguments) {
		indexApi = indexApi + 1;
		final int positionChecked = indexApi;
		Bundle bundle = new Bundle();
		bundle.putString("ranking_id", arguments.getString("ranking_id"));
		bundle.putString("type", arguments.getString("mtype"));

		executeHttps(RequestMethod.GET, API.API_R025, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
				if (positionChecked == indexApi)
					Conts.showView(loadingView, true);
			}

			@Override
			public void onError() {
				if (positionChecked == indexApi)
					onError("erorr");
			}

			@Override
			public void onError(String message) {
				if (positionChecked == indexApi) {
					Conts.showDialogThongbao(getContext(), message);
					Conts.showView(loadingView, false);
				}
			}

			@Override
			public void onSuscess(JSONObject response) {
				if (positionChecked == indexApi) {
					Conts.showView(loadingView, false);
					String quantity_in_duration = Conts.getString(response, "quantity_in_duration");
					String commission_in_duration = Conts.getString(response, "commission_in_duration");
					soGDTrongThang.setText(quantity_in_duration);
					soHHTrongThang.setText(String.format(getContext().getString(R.string.format_tien), commission_in_duration + ""));
				}
			}
		});
	}

	public void executeHttps(final RequestMethod requestMethod, final String api, final Bundle bundle, final IContsCallBack contsCallBack) {
		((VApplication) getContext().getApplicationContext()).executeHttps(requestMethod, api, bundle, contsCallBack);
	}

	private String type = "1";

	public void setType(String type) {
		this.type = type;
		// 2 so luong
		// 1 dong thu
		bxhHeader.setDoanhThu("1".equals(type));
	}
}