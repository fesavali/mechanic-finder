package com.felohscodes.onroadmech.Models;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webViewingHelper extends WebViewClient {

    private Activity activity = null;

    public webViewingHelper(Activity activity){
        this.activity = activity;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView helpBrowser, String url) {
        if(url.indexOf("aa1car.com") > -1 ) return false;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
        return super.shouldOverrideUrlLoading(helpBrowser, url);
    }
}
