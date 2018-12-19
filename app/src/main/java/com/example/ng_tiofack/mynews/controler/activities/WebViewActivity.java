package com.example.ng_tiofack.mynews.controler.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.ng_tiofack.mynews.R;
import java.util.Objects;

public class WebViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        this.configureToolbar();
        WebView webview = findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        String url = getIntent().getStringExtra(getString(R.string.articleUrl));
        webview.loadUrl(url);


    }

    private void configureToolbar() {
        //Get the toolbar (Serialise)
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }
}
