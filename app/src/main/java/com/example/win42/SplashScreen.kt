package com.example.win42

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import coil.load
import com.example.win42.model.PostModel
import com.example.win42.services.ApiCs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID


class SplashScreen : AppCompatActivity() {
    var currentDevice: String = ""
    var currentLocale: String = ""
    var currentId: String = ""
    var currentUrl: String = ""

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val imageViewBack = findViewById<ImageView>(R.id.imageView_back)
        imageViewBack.load("http://49.12.202.175/win42/background.png")
        requestPermission()
        currentDevice = Build.MANUFACTURER + Build.MODEL
        currentLocale = resources.configuration.locales.get(0).toString()
        currentId = UUID.randomUUID().toString()
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://49.12.202.175/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val response = retrofit.create(ApiCs::class.java)
            launch(Dispatchers.Main) {
                currentUrl = response.postQuery(
                    PostModel(
                        currentDevice,
                        currentLocale,
                        currentId
                    )
                ).body()?.url.toString()
                if (currentUrl == "no") {
                    val intent = Intent(this@SplashScreen, QuizActivity::class.java)
                    intent.putExtra("url", currentUrl)
                    startActivity(intent)
                    finish()
                } else if (currentUrl == "nopush") {
                    val intent = Intent(this@SplashScreen, QuizActivity::class.java)
                    intent.putExtra("url", currentUrl)
                    startActivity(intent)
                    finish()
                } else {
                    Handler().postDelayed({
                        val intent = Intent(this@SplashScreen, ShowScreen::class.java)
                        intent.putExtra("url", currentUrl)
                        startActivity(intent)
                        finish()
                    }, 3000)
                }
            }

        }
    }

    fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    listOf(Manifest.permission.POST_NOTIFICATIONS).toTypedArray(),
                    1
                )
            }
        }
    }

}
