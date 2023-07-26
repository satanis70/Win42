package com.example.win42

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class ShowScreen : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_screen)
        val url = intent.getStringExtra("url")
        Toast.makeText(this, url, Toast.LENGTH_LONG).show()
        val showView = findViewById<WebView>(R.id.myView)
        showView.webChromeClient = CustomWebChromeClient(this, findViewById(R.id.frame_layout))
        showView.settings.javaScriptEnabled = true
        if (url != null) {
            showView.loadUrl(url)
        }
    }
}

