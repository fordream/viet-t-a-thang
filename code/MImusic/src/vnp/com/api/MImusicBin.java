package vnp.com.api;

import android.os.Binder;

public class MImusicBin extends Binder {
	private MImusicService mImusicService;

	public MImusicBin(MImusicService mImusicService) {
		this.mImusicService = mImusicService;
	}

	public MImusicService getService() {
		return mImusicService;
	}
}