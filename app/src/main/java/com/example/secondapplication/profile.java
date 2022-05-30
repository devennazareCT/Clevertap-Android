package com.example.secondapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.InboxMessageButtonListener;

import java.util.HashMap;

public class profile extends AppCompatActivity implements CTInboxListener, InboxMessageButtonListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
         clevertapDefaultInstance.initializeInbox();
        findViewById(R.id.appinboxbtn).setOnClickListener(v->{
                clevertapDefaultInstance.showAppInbox();
        });
    }

    @Override
    public void inboxDidInitialize() {

    }

    @Override
    public void inboxMessagesDidUpdate() {

    }

    @Override
    public void onInboxButtonClick(HashMap<String, String> payload) {

    }
}