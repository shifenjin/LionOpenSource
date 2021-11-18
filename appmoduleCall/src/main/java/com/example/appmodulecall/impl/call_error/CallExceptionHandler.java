package com.example.appmodulecall.impl.call_error;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

public class CallExceptionHandler {

    public static final String TAG = "Call";

    public static void logWarn(CallException callException) {
        Log.w(TAG, "description : " + callException.getMessage());
        Log.w(TAG, "msg : " + callException.getMessage());
    }

    public static void logError(CallException callException) {
        Log.e(TAG, "description : " + callException.getMessage());
        Log.e(TAG, "msg : " + callException.getMessage());
    }

    public static void showToast(@NonNull Context context, @NonNull CallException callException) {
        Toast.makeText(context, callException.getDescription(), Toast.LENGTH_SHORT).show();
    }
}
