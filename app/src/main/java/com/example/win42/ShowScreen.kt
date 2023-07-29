package com.example.win42

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.onesignal.OneSignal

class ShowScreen : AppCompatActivity() {
    lateinit var showView: WebView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_screen)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId("714b9f14-381d-4fc4-a93c-28d480557381")
        val url = intent.getStringExtra("url")
        showView = findViewById(R.id.myView)
        showView.settings.javaScriptEnabled = true
        showView.settings.domStorageEnabled = true
        showView.settings.useWideViewPort = true
        showView.settings.databaseEnabled = true
        showView.settings.builtInZoomControls = true
        showView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        showView.settings.setSupportZoom(true)
        showView.settings.allowFileAccess = true
        showView.settings.loadWithOverviewMode = true
        showView.isClickable = true
        showView.webChromeClient = CustomWebChromeClient(this, findViewById(R.id.frame_layout))
        showView.webViewClient = CustomWebViewClient(showView)
        showView.settings.supportMultipleWindows()
        showView.settings.allowContentAccess = true
        showView.settings.setNeedInitialFocus(true)

        if (savedInstanceState != null){
            showView.restoreState(savedInstanceState)
        } else {
            if (url != null) {
                showView.loadUrl(url)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        showView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (showView.canGoBack()) {
            showView.goBack()
        } else {
            finish()
        }
    }
}

