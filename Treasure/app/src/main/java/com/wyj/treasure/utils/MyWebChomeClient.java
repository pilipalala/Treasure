package com.wyj.treasure.utils;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * MyWebChomeClient
 */
public class MyWebChomeClient extends WebChromeClient {
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        mOpenFileChooserCallBack.onReceivedTitle(view, title);

    }
    private OpenFileChooserCallBack mOpenFileChooserCallBack;

    public MyWebChomeClient(OpenFileChooserCallBack openFileChooserCallBack) {
        mOpenFileChooserCallBack = openFileChooserCallBack;
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        return mOpenFileChooserCallBack.openFileChooserCallBackAndroid5(webView, filePathCallback, fileChooserParams);
    }

    public interface OpenFileChooserCallBack {
        // for API - Version below 5.0.
        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);

        void onReceivedTitle(WebView view, String title);

        // for API - Version above 5.0 (contais 5.0).
        boolean openFileChooserCallBackAndroid5(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                                FileChooserParams fileChooserParams);
    }


}
