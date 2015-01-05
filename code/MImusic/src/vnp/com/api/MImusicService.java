package vnp.com.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MImusicService extends Service {
	public static final String ACTION = "vnp.com.api.MImusicService";
	public static final String KEY = "KEY";

	public MImusicService() {
		super();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

}
