package jat.imview.network;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by bulat on 07.12.15.
 */
public class SendService extends IntentService {
    private static final String HOST = "52.25.145.110";
    public SendService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
