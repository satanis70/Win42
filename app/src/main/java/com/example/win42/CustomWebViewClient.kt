package com.example.win42

import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlin.math.roundToInt


class CustomWebViewClient(val mWebView: WebView): WebViewClient(){
    private var mHasToRestoreState = false
    private val mProgressToRestore = 0f
    override fun onPageFinished(view: WebView, url: String?) {
        if (mHasToRestoreState) {
            mHasToRestoreState = false
            view.postDelayed(
                {
                    val webviewsize: Int = mWebView.contentHeight - mWebView.top
                    val positionInWV: Float = webviewsize * mProgressToRestore
                    val positionY = (mWebView.top + positionInWV).roundToInt()
                    mWebView.scrollTo(0, positionY)
                },
                300
            )
        }
        super.onPageFinished(view, url)
    }
}