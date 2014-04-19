package com.teamlrp.shinedotcom;

import android.util.Log;

/**
 * Created by laavanye bahl on 4/20/2014.
 */
public class AppLog {
    private static final String APP_TAG = "AudioRecorder";

    public static int logString(String message) {
        return Log.i(APP_TAG, message);
    }
}
