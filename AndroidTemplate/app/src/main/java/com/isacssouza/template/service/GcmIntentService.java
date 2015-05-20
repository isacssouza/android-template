package com.isacssouza.template.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.isacssouza.template.receiver.GcmBroadcastReceiver;

/**
 * Service for processing push notifications.
 * <p/>
 * Created by isacssouza on 4/16/15.
 */
public class GcmIntentService extends IntentService {
    private static final String TAG = GcmIntentService.class.getSimpleName();

    public GcmIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Received intent: " + intent);

        // TODO: handle push here

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}
