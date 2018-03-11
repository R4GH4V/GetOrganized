package com.example.android.getorganized;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShopDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_display);

        Intent page = getIntent();
        String myPage = page.getStringExtra("WEB_PAGE");
        WebView myWebView =  findViewById( R.id.webPageView );
        myWebView.setWebViewClient( new WebViewClient() );
        myWebView.loadUrl(myPage);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}