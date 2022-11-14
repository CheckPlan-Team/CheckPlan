package com.example.checkplan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.checkplan.databinding.ActivityInputTodoBinding
import android.os.Handler

//할일 목록을 작성(편집)하기 위한 액티비티
class InputTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val InputTodoBinding = ActivityInputTodoBinding.inflate(layoutInflater)
        setContentView(InputTodoBinding.root)

        //
    }
}
