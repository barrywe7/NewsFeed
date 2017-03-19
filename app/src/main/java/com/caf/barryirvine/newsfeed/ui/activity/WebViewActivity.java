package com.caf.barryirvine.newsfeed.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.caf.barryirvine.newsfeed.R;

public class WebViewActivity extends AppCompatActivity {

    public static void start(@NonNull final Activity activity, @NonNull final String title, @NonNull final String url) {
        ActivityCompat.startActivity(activity, makeIntent(activity, title, url), null);
    }


    protected static Intent makeIntent(@NonNull final Context context, @NonNull final String title, @NonNull final String url) {
        return new Intent(context, WebViewActivity.class)
                .putExtra(Extras.URL, url)
                .putExtra(Extras.TITLE, title);
    }


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        final WebView webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getIntent().getStringExtra(Extras.URL));
        setupActionBar(getIntent().getStringExtra(Extras.TITLE));
    }

    private void setupActionBar(final String url) {
        setTitle(url);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private static class Extras {
        private static final String TITLE = "TITLE";
        private static final String URL = "EXTRA_URL";
    }
}