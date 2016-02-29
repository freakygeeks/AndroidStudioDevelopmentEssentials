package com.chrysalis.serviceexample;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ~chrysalis~ on 11/13/2015.
 */
public class MyIntentService extends IntentService {

    private static final String TAG = "ServiceExample";

    @Override
    protected void onHandleIntent(Intent arg0) {
        Log.i(TAG, "Intent Service Started");
    }

    public MyIntentService() {
        super("MyIntentService");
    }
}
