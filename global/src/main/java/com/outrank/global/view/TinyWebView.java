package com.outrank.global.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.util.AttributeSet;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.outrank.global.utils.LogUtils;


/**
 * Created by Gabriel on 2018/4/3.
 * Email 17600284843@163.com
 */

public class TinyWebView extends WebView {

    public TinyWebView(Context context) {
        super(context);
    }

    public TinyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TinyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        openJavaScript();
        setWebViewClient(new MyWebViewClient());
        setWebChromeClient(new WebChromeClient());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void openJavaScript() {
        WebSettings settings = getSettings();
// 设置允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDisplayZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(false);
        //解决某些标签不支出存现白屏问题
        settings.setDomStorageEnabled(false);
        settings.setSupportMultipleWindows(true);
    }


    /**
     * 类描述：显示WebView加载的进度情况
     */
    public class WebChromeClient extends android.webkit.WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }


        @Override//设置响应js的Alert()函数
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            if (mOnJsAlert != null) {
                mOnJsAlert.onJsAlert(result, message);
            }
            return true;
        }

    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            LogUtils.e("onPageStarted--url--->" + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtils.e("onPageFinished--url--->" + url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.e("shouldOverrideUrlLoading--url--->" + url);
            if (!url.contains("blob:")) {
                loadUrl(url);
            }
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            if (error.getPrimaryError() == SslError.SSL_DATE_INVALID
                    || error.getPrimaryError() == SslError.SSL_EXPIRED
                    || error.getPrimaryError() == SslError.SSL_INVALID
                    || error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
                handler.proceed();
            } else {
                handler.cancel();
            }
        }
    }




    /*----------------onJsAlert监听----------------*/

    public interface onJsAlert {
        void onJsAlert(JsResult result, String message);
    }

    private onJsAlert mOnJsAlert;

    public void addOnJSAlert(onJsAlert onJsAlerts) {
        mOnJsAlert = onJsAlerts;
    }


}
