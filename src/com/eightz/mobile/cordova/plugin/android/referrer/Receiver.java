package com.eightz.mobile.cordova.plugin.android.referrer;

import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Receiver extends BroadcastReceiver {

@Override
public void onReceive(Context context, Intent intent) {

    Bundle extras = intent.getExtras();
    if (extras != null) {
    	String referrerString = extras.getString("referrer");
        if (referrerString != null && !referrerString.contains("organic")) {
            trackReferrerAttributes(context, referrerString);
        }
    }
}

private void trackReferrerAttributes(Context context, String rawReferrer) {
        String referrer;
        try {
            referrer = URLDecoder.decode(rawReferrer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("Receiver", "Error in decoding referrer code into UTF-8 form");
            Log.e("Receiver", e.getMessage(), e);
            return;
        }

        String json = "{\"referrer\":\"" + referrer + "\"}";
        Log.d("Receiver", json);
        SharedPreferences sharedPreferences = context.getSharedPreferences("NativeStorage", Context.MODE_PRIVATE);
    	Editor edit = sharedPreferences.edit();
    	edit.putString("referrer", json);
    	edit.commit();
    }
 
} // end of class

