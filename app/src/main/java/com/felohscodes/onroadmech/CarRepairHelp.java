package com.felohscodes.onroadmech;

import android.os.Bundle;

import com.felohscodes.onroadmech.Models.webViewingHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
public class CarRepairHelp extends AppCompatActivity {

    private WebView helpBrowser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_repair_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Toast.makeText(this, "This site is not owned by Onroad Mechs and may contain ads.", Toast.LENGTH_LONG).show();

        this.helpBrowser = (WebView) findViewById(R.id.web_help);

        WebSettings webSettings = helpBrowser.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webViewingHelper viewclientHelper = new webViewingHelper(this);
        helpBrowser.setWebViewClient(viewclientHelper);

        helpBrowser.loadUrl("https://www.aa1car.com/");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.helpBrowser.canGoBack()){
            this.helpBrowser.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
