package com.example.checkplan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        var mainintent : Intent = Intent(this, InputTodoActivity::class.java)
        startActivity(mainintent)
    }
}