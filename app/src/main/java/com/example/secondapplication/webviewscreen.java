package com.example.secondapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;

import com.clevertap.android.sdk.CTWebInterface;
import com.clevertap.android.sdk.CleverTapAPI;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;

public class webviewscreen extends AppCompatActivity {
    private WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviewscreen);
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);

        browser=(WebView)findViewById(R.id.webView);
        browser.setWebViewClient(new MyBrowser());
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new CTWebInterface(CleverTapAPI.getDefaultInstance(this)),"CleverTap");

        //to enable alerts
        browser.setWebChromeClient(new WebChromeClient());

        browser.loadUrl("https://infinitival-floor.000webhostapp.com/pwa/index.html");
    }
}
class MyBrowser extends WebViewClient
{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        view.loadUrl(url);
        return true;
    }
}