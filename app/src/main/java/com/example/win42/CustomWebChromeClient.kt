package com.example.win42

import android.R
import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts


class CustomWebChromeClient(val activity: Activity, val fullscreenContainer: FrameLayout) :
    WebChromeClient() {
    private var customView: View? = null
    private var customViewCallback: CustomViewCallback? = null
    private var originalOrientation = 0


    override fun onHideCustomView() {
        if (customView == null) {
            return
        }
        fullscreenContainer.removeView(customView)
        fullscreenContainer.visibility = View.GONE
        customView = null
        customViewCallback!!.onCustomViewHidden()
        activity.requestedOrientation = originalOrientation
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        if (customView != null) {
            onHideCustomView()
            return
        }
        customView = view
        originalOrientation = activity.requestedOrientation
        customViewCallback = callback
        fullscreenContainer.addView(
            customView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        fullscreenContainer.visibility = View.VISIBLE
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}