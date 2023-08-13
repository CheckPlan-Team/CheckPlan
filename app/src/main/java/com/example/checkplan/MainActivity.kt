package com.example.checkplan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.example.CustomView.CustomCalendar
import com.example.DBHelper.DatabaseHelper
import com.example.checkplan.databinding.ActivityMainBinding
import com.example.checkplan.databinding.FragmentPlanManagementBinding
import com.google.android.material.tabs.TabLayout
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val databaseHelper: DatabaseHelper by lazy{
        DatabaseHelper.getInstance(applicationContext)
    }

    //설명 : 액티비티 생성
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //일정 관리 기능 초기화
        addTodo()
        removeTodo()
        updateTodo()
        getAllTodo()

        val customCalendar = CustomCalendar(binding.customCalendar)
            customCalendar.editCalendar()
            customCalendar.applyDecorator()
            customCalendar.setEndTimeCalendar()

        //날짜 선택 기능 초기화
        setSelectedDate()

        setContentView(binding.root)
    }


    //설명 : 액티비티 소멸
    override fun onDestroy() {
        databaseHelper.close()
        super.onDestroy()
    }

    //설명 : 날짜 선택 시 날짜를 출력, 이전 날짜를 선택 시 보기 버튼만 활성화, 오늘 혹은 이후 날짜 선택 시 모든 일정 관리 버튼 활성화
    private fun setSelectedDate(){
        /*widget은 MaterialCanendarView, date는 선택된 날짜, selected 날짜가 선택되었는지에 대한 상태*/
        binding.customCalendar.setOnDateChangedListener { widget, date, selected ->
            println(date.date.toString())
            println(date.year.toString())
            println(date.month.toString())
            println(date.day.toString())
            val localDateOf: LocalDate = LocalDate.of(date.year,date.month+1,date.day)
            binding.outputDate.text = localDateOf.toString()
            if(date.isBefore(CalendarDay.today())){
                binding.insertTodoBtn.visibility = View.INVISIBLE
                binding.deleteTodoBtn.visibility = View.INVISIBLE
                binding.updateTodoBtn.visibility = View.INVISIBLE
            }else{
                binding.insertTodoBtn.visibility = View.VISIBLE
                binding.deleteTodoBtn.visibility = View.VISIBLE
                binding.updateTodoBtn.visibility = View.VISIBLE
            }
        }
    }


    //설명 : 일정 보기
    private fun showTodo(text: String){
        binding.outputTerminal.append(text + "\n")
    }


    //설명 : 텍스트 편집기 초기화
    private fun clearEditTexts(){
        with(binding){
            editID.setText("")
            editTodo.setText("")
        }
    }


    //설명 : 날짜를 클릭하여 그 날짜에 일정을 추가
    private fun addTodo(){
        binding.insertTodoBtn.setOnClickListener {
            try{
                databaseHelper.insertData(
                    binding.outputDate.text.toString().trim(),
                    binding.editTodo.text.toString().trim()
                )
                clearEditTexts()
                showTodo("일정이 추가됨")
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    //설명 : 날짜를 누르고 id와 일정을 입력하면 기존 일정이 변경됨
    private fun updateTodo(){
        binding.updateTodoBtn.setOnClickListener {
            try{
                databaseHelper.updateData(
                    binding.editID.text.toString().trim(),
                    binding.editTodo.text.toString().trim()
                )
                clearEditTexts()
                showTodo("일정이 변경됨")
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    //설명 : 날짜를 선택하고 id 번호를 입력하면 그 id에 해당하는 일정이 삭제됨
    private fun removeTodo(){
        binding.deleteTodoBtn.setOnClickListener {
            try{
                databaseHelper.deleteData(binding.editID.text.toString().trim())
                clearEditTexts()
                showTodo("일정이 삭제됨")
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }


    //설명 : 선택된 날짜와 관련된 모든 일정 가져오기
    private fun getAllTodo(){
        binding.viewTodoBtn.setOnClickListener {
             try{
                 val selectResult = databaseHelper.getAllData(binding.outputDate.text.toString().trim())
                 showTodo(selectResult)
             }catch(e: Exception){
                 e.printStackTrace()
             }
        }
    }
}