package com.example.win42

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.win42.model.Question
import com.example.win42.services.ApiCs
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class QuizActivity : AppCompatActivity() {
    private var arrayList = ArrayList<Question>()
    private var position = 0
    lateinit var buttonNext: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId("714b9f14-381d-4fc4-a93c-28d480557381")
        val ivQuiz = findViewById<ImageView>(R.id.quiz_image_back)
        ivQuiz.load("http://49.12.202.175/win42/background.png")
        buttonNext = findViewById(R.id.button_next)
        val url = intent.getStringExtra("url")
        if (url=="nopush"){
            OneSignal.disablePush(true)
        }
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = Retrofit.Builder().baseUrl("http://49.12.202.175/win42/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiCs::class.java)
            for (i in retrofit.getCsgoModel().awaitResponse().body()!!.questions) {
                arrayList.add(i)
            }
            launch(Dispatchers.Main) {
                showQuiz()
                buttonNext.setOnClickListener {
                    if (position==arrayList.size-1){
                        startActivity(Intent(this@QuizActivity, QuizActivity::class.java))
                        finish()
                    } else {
                        position+=1
                        showQuiz()
                    }
                }
            }
        }
    }

    private fun showQuiz() {
        val button1 = findViewById<Button>(R.id.b_answer1)
        val button2 = findViewById<Button>(R.id.b_answer2)
        val button3 = findViewById<Button>(R.id.b_answer3)
        val button4 = findViewById<Button>(R.id.b_answer4)
        val tvQuestion = findViewById<TextView>(R.id.textView_question)
        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        buttonNext.isEnabled = false
        tvQuestion.text = arrayList[position].question
        button1.text = arrayList[position].answer1.name
        button2.text = arrayList[position].answer2.name
        button3.text = arrayList[position].answer3.name
        button4.text = arrayList[position].answer4.name
        button1.setBackgroundColor(resources.getColor(R.color.button_color))
        button2.setBackgroundColor(resources.getColor(R.color.button_color))
        button3.setBackgroundColor(resources.getColor(R.color.button_color))
        button4.setBackgroundColor(resources.getColor(R.color.button_color))
        button1.setOnClickListener {
            button1.isEnabled = false
            button2.isEnabled = false
            button3.isEnabled = false
            button4.isEnabled = false
            if (arrayList[position].answer1.trueorfalse == "true") {
                button1.setBackgroundColor(resources.getColor(R.color.green))
            } else {
                button1.setBackgroundColor(resources.getColor(R.color.red))
            }
            buttonNext.isEnabled = true
        }
        button2.setOnClickListener {
            button1.isEnabled = false
            button2.isEnabled = false
            button3.isEnabled = false
            button4.isEnabled = false
            if (arrayList[position].answer2.trueorfalse == "true") {
                button2.setBackgroundColor(resources.getColor(R.color.green))
            } else {
                button2.setBackgroundColor(resources.getColor(R.color.red))
            }
            buttonNext.isEnabled = true
        }
        button3.setOnClickListener {
            button1.isEnabled = false
            button2.isEnabled = false
            button3.isEnabled = false
            button4.isEnabled = false
            if (arrayList[position].answer3.trueorfalse == "true") {
                button3.setBackgroundColor(resources.getColor(R.color.green))
            } else {
                button3.setBackgroundColor(resources.getColor(R.color.red))
            }
            buttonNext.isEnabled = true
        }
        button4.setOnClickListener {
            button1.isEnabled = false
            button2.isEnabled = false
            button3.isEnabled = false
            button4.isEnabled = false
            if (arrayList[position].answer4.trueorfalse == "true") {
                button4.setBackgroundColor(resources.getColor(R.color.green))
            } else {
                button4.setBackgroundColor(resources.getColor(R.color.red))
            }
            buttonNext.isEnabled = true
        }
    }
}