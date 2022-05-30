package com.example.secondapplication;

import android.app.Application;
import android.os.Bundle;

import com.adjust.sdk.LogLevel;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.clevertap.android.sdk.pushnotification.amp.CTPushAmpListener;

public class myApplication extends Application implements CTPushAmpListener {
    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);

        super.onCreate();
        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
        String appToken = "lysplpl4f75s";
        String environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        AdjustConfig config = new AdjustConfig(this, appToken, environment);
        Adjust.onCreate(config);
        config.setLogLevel(LogLevel.WARN);

    }

    @Override
    public void onPushAmpPayloadReceived(Bundle extras) {
        CleverTapAPI.createNotification(getApplicationContext(), extras);
    }
}
