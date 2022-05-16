package com.example.secondapplication;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.pushnotification.NotificationInfo;
import com.clevertap.android.sdk.pushnotification.amp.CTPushAmpListener;
import com.clevertap.android.sdk.pushnotification.fcm.CTFcmMessageHandler;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class MyFcmMessageListenerService extends FirebaseMessagingService  {
    @Override
    public void onMessageReceived(RemoteMessage message){

           //  new CTFcmMessageHandler().processPushAmp(getApplicationContext(), message);
            Bundle extras = new Bundle();
            if (message.getData().size() > 0) {

                for (Map.Entry<String, String> entry : message.getData().entrySet()) {
                    extras.putString(entry.getKey(), entry.getValue());
                }
            }
                NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);

                if (!info.fromCleverTap) {
                    Log.d("FCM data", "FCM data: " + new Gson().toJson(message));   // to print payload
                    Log.d("img", "img: " + message.getNotification().getImageUrl());
                    Log.d("title", "title = [" + message.getNotification().getTitle() + "]");
                    Log.d("message", "message = [" + message.getNotification().getBody() + "]");

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    int notificationId = new Random().nextInt(60000);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "111")

                            .setSmallIcon(R.drawable.ic_baseline_share_24)  //a resource for your custom small icon
                            .setColor(Color.YELLOW) //small icon bg color
                            .setContentTitle(message.getNotification().getTitle()) //the "title" value you sent in your notification
                            .setContentText(message.getNotification().getBody()) //ditto
                            .setAutoCancel(true);  //dismisses the notification on click

                    notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
                    Log.d("FBNOTIFY","This is from Frebase");

                }
                else {
                   new  CTFcmMessageHandler().createNotification(getApplicationContext(), message);
                   // CleverTapAPI.createNotification(getApplicationContext(), extras);
                    Log.d("CTNOTIFY","This is from Clevertap");
                    // not from CleverTap handle yourself or pass to another provider
                }
            }


//
//        if (message.getData().size() > 0) {
//            Bundle extras = new Bundle();
//            Iterator var = message.getData().entrySet().iterator();
//            while(var.hasNext()) {
//                Map.Entry entry = (Map.Entry)var.next();
//                extras.putString((String)entry.getKey(), (String)entry.getValue());
//            }
//            CleverTapAPI.processPushNotification(getApplicationContext(),extras);
//        }

    }

