package com.example.secondapplication;

import android.app.Application;

import com.adjust.sdk.LogLevel;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
public class myApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
        ActivityLifecycleCallback.register(this);
        String appToken = "lysplpl4f75s";
        String environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        AdjustConfig config = new AdjustConfig(this, appToken, environment);
        Adjust.onCreate(config);
        config.setLogLevel(LogLevel.WARN);

    }
}
