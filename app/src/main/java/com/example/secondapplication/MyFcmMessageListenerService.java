package com.example.secondapplication;
import com.clevertap.android.sdk.pushnotification.fcm.CTFcmMessageHandler;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFcmMessageListenerService extends FirebaseMessagingService {

        @Override
        public void onMessageReceived(RemoteMessage message){
            super.onMessageReceived(message);

            new CTFcmMessageHandler().createNotification(getApplicationContext(), message);
        }


}
