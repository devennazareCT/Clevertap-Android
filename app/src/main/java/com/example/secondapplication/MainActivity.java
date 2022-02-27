package com.example.secondapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;
import com.clevertap.android.sdk.inbox.CTInboxActivity;
import com.clevertap.android.sdk.inbox.CTInboxMessage;
import com.clevertap.android.sdk.InboxMessageButtonListener;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CTInboxListener ,InboxMessageButtonListener,DisplayUnitListener {
    private Button mButton;
    private Button notificationbtn;
    private Button demo6;
    private CardView c;
    private TextView title;
    private TextView mssg;
    private ImageView imgview;
    private int Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
        CleverTapAPI.createNotificationChannel(getApplicationContext(),"111","Channel111","TestChannel", NotificationManager.IMPORTANCE_MAX,true);
        if (clevertapDefaultInstance != null) {
            //Set the Notification Inbox Listener
            clevertapDefaultInstance.setCTNotificationInboxListener(this);
            //Initialize the inbox and wait for callbacks on overridden methods
           // clevertapDefaultInstance.initializeInbox();
        }
        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);
        HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
        prodViewedAction.put("Product Name", "Casio Chronograph Watch");
        prodViewedAction.put("Category", "Mens Accessories");
        prodViewedAction.put("Price", 59.99);
        prodViewedAction.put("Date", new java.util.Date());

        clevertapDefaultInstance.pushEvent("Product viewed", prodViewedAction);
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
                profileUpdate.put("Name", "Deven Nazare");    // String
                profileUpdate.put("Identity", 291198);      // String or number
                profileUpdate.put("Email", "deven@clevertap.com"); // Email address of the user
                profileUpdate.put("Phone", "8779636312");   // Phone (with the country code, starting with +)
                profileUpdate.put("Gender", "M");             // Can be either M or F
                profileUpdate.put("DOB", new Date());         // Date of Birth. Set the Date object to the appropriate value first
// optional fields. controls whether the user will be sent email, push etc.
                profileUpdate.put("MSG-email", false);        // Disable email notifications
                profileUpdate.put("MSG-push", true);          // Enable push notifications
                profileUpdate.put("MSG-sms", false);          // Disable SMS notifications
                profileUpdate.put("MSG-whatsapp", true);      // Enable WhatsApp notifications
                profileUpdate.put("Photo", "https://ca.slack-edge.com/T02HPQ6N3-U02TN313BEF-29856d0727ff-512");
                ArrayList<String> stuff = new ArrayList<String>();
                stuff.add("bag");
                stuff.add("shoes");
                profileUpdate.put("MyStuff", stuff);                        //ArrayList of Strings
                String[] otherStuff = {"Jeans","Perfume"};
                profileUpdate.put("MyStuff", otherStuff);                   //String Array
                clevertapDefaultInstance.pushEvent("Product viewed");

                clevertapDefaultInstance.onUserLogin(profileUpdate);

            }



        });


        notificationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clevertapDefaultInstance.pushEvent("DevenAppInbox");
                Toast.makeText(getApplicationContext(),"Notification Button CLicked",Toast.LENGTH_SHORT).show();

                inboxDidInitialize();
            }
        });

        demo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clevertapDefaultInstance.pushEvent("NativeDisplayDeven");
                Toast.makeText(getApplicationContext(),"Trail Button CLicked",Toast.LENGTH_SHORT).show();

//               onDisplayUnitsLoaded();
            }
        });

    }

    @Override
    public void inboxDidInitialize() {
        CleverTapAPI ct = CleverTapAPI.getDefaultInstance(getApplicationContext());
        ct.showAppInbox();//Opens Activity with default style configs
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


    @Override
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

}
