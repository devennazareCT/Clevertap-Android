package com.example.secondapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.adjust.sdk.AdjustEvent;
import com.adjust.sdk.LogLevel;
import com.appsflyer.AppsFlyerLib;
import com.adjust.sdk.Adjust;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.Utils;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;
import com.clevertap.android.sdk.inbox.CTInboxActivity;
import com.clevertap.android.sdk.inbox.CTInboxMessage;
import com.clevertap.android.sdk.InboxMessageButtonListener;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.clevertap.android.sdk.interfaces.OnInitCleverTapIDListener;
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener;
import com.clevertap.android.sdk.pushnotification.amp.CTPushAmpListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CTInboxListener ,InboxMessageButtonListener,DisplayUnitListener {
    private Button mButton;
    private Button notificationbtn;
    private Button demo6;
    private CardView c;
    private TextView title;
    private TextView mssg;
    private ImageView imgview;
    private int Image;
   private Button webv;

    protected void onResume() {
        super.onResume();
        Adjust.onResume();
    }
    protected void onPause() {
        super.onPause();
        Adjust.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        Task<String> fcmRegId = FirebaseMessaging.getInstance().getToken();
        clevertapDefaultInstance.pushFcmRegistrationId(fcmRegId.toString(),true);
        Log.d("mytoken", "mytoken: "+fcmRegId.toString());

      //x     clevertapDefaultInstance.setCTPushNotificationListener(this);
   //     MiPushClient.registerToken(this,"2882303761520171226","5902017164226");
        MiPushClient.registerPush(this, "2882303761520171226", "5902017164226");
            String xiaomiToken = MiPushClient.getRegId(this);
        if(xiaomiToken != null){

            clevertapDefaultInstance.pushXiaomiRegistrationId(xiaomiToken,true);
            Log.d("xiomisuc","CleverTap is not null"+xiaomiToken);
        }else{
            Log.d("xiomipush","CleverTap is NULL"+xiaomiToken);
        }
        CleverTapAPI.setDebugLevel(3);

        AppsFlyerLib.getInstance().init("TFXzofCdhmGvhCcWReFiNd", null, this);
        AppsFlyerLib.getInstance().setDebugLog(true);

        AppsFlyerLib.getInstance().start(this);
        AppsFlyerLib.getInstance().start(getApplicationContext(), "TFXzofCdhmGvhCcWReFiNd\n", new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {
                Log.d("tagapps", "Launch sent successfully, got 200 response code from server");
            }

            @Override
            public void onError(int i, @NonNull String s) {
                Log.d("tagapp2", "Launch failed to be sent:\n" +
                        "Error code: " + i + "\n"
                        + "Error description: " + s);
            }
        });
        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
      //  clevertapDefaultInstance.setCTPushAmpListener(this);
        CleverTapAPI.createNotificationChannel(getApplicationContext(),"111","Channel111","TestChannel", NotificationManager.IMPORTANCE_MAX,true);
        if (clevertapDefaultInstance != null) {
            //Set the Notification Inbox Listener
          //  clevertapDefaultInstance.setCTNotificationInboxListener(this);
            //Initialize the inbox and wait for callbacks on overridden methods
         //   clevertapDefaultInstance.initializeInbox();
        }
        CleverTapAPI.getDefaultInstance(getApplicationContext()).getCleverTapID(new OnInitCleverTapIDListener() {
            @Override
            public void onInitCleverTapID(final String cleverTapID) {
                // Callback on main thread
                Log.d(cleverTapID,"ctidd");
                AppsFlyerLib.getInstance().setCustomerUserId(cleverTapID);
                Adjust.addSessionPartnerParameter("clevertap_id", cleverTapID);


            }
        });

        //For manually sending FCM Token to CleverTap Push
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
//                        sendFCMTokenToDatabase(task.getResult());
                        Toast.makeText(getApplicationContext(),"Notification Token is"+task.getResult(),Toast.LENGTH_SHORT).show();

                        clevertapDefaultInstance.pushFcmRegistrationId(task.getResult(),true);
                    }
                });
//        String fcmRegId = FirebaseInstanceId.getInstance().getToken();
//        clevertapDefaultInstance.pushFcmRegistrationId(fcmRegId,true);

        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);

        HashMap<String, Object> link = new HashMap<String, Object>();

        link.put("Link", "cleverTap.com");


        clevertapDefaultInstance.pushEvent("Link Clicked", link);

//        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);

        mButton = findViewById(R.id.button1);
        notificationbtn = findViewById(R.id.nt1);
        demo6 = findViewById(R.id.b6);
        c=findViewById(R.id.c1);
        title=findViewById(R.id.native_display_title);
        mssg=findViewById(R.id.native_display_message);
        webv=findViewById(R.id.webview);
      //  imgview=findViewById(R.id.imageviewcli);
     //   imgview=(ImageView)findViewById(R.id.imageviewcli);
        String imageUri = "https://image.freepik.com/free-vector/elegant-white-background-with-shiny-lines_1017-17580.jpg";
        ImageView ivBasicImage = (ImageView) findViewById(R.id.imageviewcli);
        Picasso.get().load(imageUri).into(ivBasicImage);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
//                profileUpdate.put("Name", "Sachin");    // String
//                profileUpdate.put("Identity", 78010);      // String or number
//                profileUpdate.put("Email", "sachin@clevertap.com"); // Email address of the user
//                profileUpdate.put("Phone", "55666171");   // Phone (with the country code, starting with +)
//                profileUpdate.put("Gender", "M");
//                profileUpdate.put("Language", "Hindi");


                profileUpdate.put("Name", "Prerna");    // String
                profileUpdate.put("Identity", 4161);      // String or number
                profileUpdate.put("Email", "prerna@clevertap.com"); // Email address of the user
                profileUpdate.put("Phone", "8779636312");   // Phone (with the country code, starting with +)
                profileUpdate.put("Gender", "M");
                profileUpdate.put("Language", "Marathi");
                // Can be either M or F
                profileUpdate.put("DOB", new Date());         // Date of Birth. Set the Date object to the appropriate value first
// optional fields. controls whether the user will be sent email, push etc.
                profileUpdate.put("MSG-email", true);        // Disable email notifications
                profileUpdate.put("MSG-push", true);          // Enable push notifications
                profileUpdate.put("MSG-sms", false);          // Disable SMS notifications
                profileUpdate.put("MSG-whatsapp", true);      // Enable WhatsApp notifications
                profileUpdate.put("Photo", "https://ca.slac12");
                ArrayList<String> stuff = new ArrayList<String>();
                stuff.add("bag");
                stuff.add("shoes");
                profileUpdate.put("MyStuff", stuff);                        //ArrayList of Strings
                String[] otherStuff = {"Jeans","Perfume"};
                profileUpdate.put("MyStuff", otherStuff);                   //String Array
             //   clevertapDefaultInstance.pushEvent("Product viewed");

                clevertapDefaultInstance.onUserLogin(profileUpdate);

            }



        });


        notificationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cleverTapId = clevertapDefaultInstance.getCleverTapID();
                AdjustEvent adjustEvent = new AdjustEvent("yuwytb");
                adjustEvent.addPartnerParameter("eventname", "testevent");


                AdjustEvent adjustEvent2 = new AdjustEvent("mw4e53");
                adjustEvent.addPartnerParameter("productname", "iPhone");
                adjustEvent.addPartnerParameter("clevertapId", cleverTapId);

                Adjust.trackEvent(adjustEvent);

                Adjust.trackEvent(adjustEvent2);

                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put("Product Name", "Fossil Watch");
                prodViewedAction.put("Category", "Mens Accessories");
                prodViewedAction.put("Price", 59.99);
                prodViewedAction.put("Identity", 89022);

                prodViewedAction.put("Date", new java.util.Date());

                clevertapDefaultInstance.pushEvent("Product viewed", prodViewedAction);

             clevertapDefaultInstance.pushEvent("DevenAppInbox");
                // Toast.makeText(getApplicationContext(),"Notification Button CLicked",Toast.LENGTH_SHORT).show();

            }
        });
        webv.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, webviewscreen.class);
                startActivity(intent);

            }
        });
        demo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inboxDidInitialize();
                String cleverTapId = clevertapDefaultInstance.getCleverTapID();

                AdjustEvent adjustEvent2 = new AdjustEvent("mw4e53");
                adjustEvent2.addPartnerParameter("clevertapId", cleverTapId);


                Adjust.trackEvent(adjustEvent2);

                //  clevertapDefaultInstance.pushEvent("NativeDisplayDeven");
                Toast.makeText(getApplicationContext(),"Trail Button CLicked",Toast.LENGTH_SHORT).show();

//               onDisplayUnitsLoaded();
            }
        });

    }

    @Override
    public void inboxDidInitialize() {
        //Opens Activity with default style configs
        CleverTapAPI ct = CleverTapAPI.getDefaultInstance(getApplicationContext());
      //  ct.showAppInbox();
        Log.d("myTag", "Inbox Initialized");


    }

    @Override
    public void inboxMessagesDidUpdate() {
        Toast.makeText(getApplicationContext(),"Inbox Updated",Toast.LENGTH_SHORT).show();
        Log.d("myTag", "Inbox Updated");


    }

    @Override
    public void onInboxButtonClick(HashMap<String, String> payload) {
        Log.d("myTag", "Inbox Clicked");
        JSONObject jsonPayload = new JSONObject(payload);

        final String json = "JSON RESPONSE IS =>:{" + jsonPayload.toString() + "}";


        Toast.makeText(getApplicationContext(),json,Toast.LENGTH_SHORT).show();

    }



    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        //public CleverTapDisplayUnit getDisplayUnitForId();
        for (int i=0;i<units.size();i++) {
            CleverTapDisplayUnit unit = units.get(i);
            System.out.println(unit);
            prepareDisplayView(unit);
        }

    }


    private void prepareDisplayView(CleverTapDisplayUnit unit) {

        for(CleverTapDisplayUnitContent i:unit.getContents()) {
            title.setText(i.getTitle());
            mssg.setText(i.getMessage());
            try {
                String imageUri = i.getMedia();
                ImageView ivBasicImage = (ImageView) findViewById(R.id.imageviewcli);
                Picasso.get().load(imageUri).into(ivBasicImage);
            } finally {

            }


            Log.d("myTag", String.valueOf(Uri.parse(i.getMedia())));

        }

        //Notification Viewed Event
        CleverTapAPI.getDefaultInstance(this).pushDisplayUnitViewedEventForID(unit.getUnitID());

        //Notification Clicked Event
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CleverTapAPI.getDefaultInstance(getApplicationContext()).pushDisplayUnitClickedEventForID(unit.getUnitID());

            }
        });
    }


//    @Override
//    public void onPushAmpPayloadReceived(Bundle extras) {
//     CleverTapAPI.createNotification(getApplicationContext(), extras);
//        Log.d("PUSHAMP", "this is through pushamp"+extras);
//    }
}
