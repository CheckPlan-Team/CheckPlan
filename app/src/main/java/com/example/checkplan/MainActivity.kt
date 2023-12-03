package com.example.checkplan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.CustomView.CustomCalendar
import com.example.DBHelper.DatabaseHelper
import com.example.checkplan.databinding.ActivityMainBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val databaseHelper: DatabaseHelper by lazy{
        DatabaseHelper.getInstance(applicationContext)
    }

    private var todo: Todo? = null


    //설명 : 액티비티 생성
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //일정 관리 기능 초기화
        addTodo()
        removeTodo()
        updateTodo()

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


    //설명 : 날짜 선택 시 날짜를 출력
    private fun getDate(date: CalendarDay){
        val localDateOf: LocalDate = LocalDate.of(date.year,date.month+1,date.day)
        binding.outputDate.text = localDateOf.toString()
    }

    //설명 : 오늘 혹은 이후 날짜 선택 시 버튼을 보여주고, 이전 날짜를 선택 시 버튼을 숨김
    private fun hideOrShowButton(date: CalendarDay){
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


    //설명 : 날짜 선택 이벤트 설정
    private fun setSelectedDate(){
        /*widget은 MaterialCanendarView, date는 선택된 날짜, selected 날짜가 선택되었는지에 대한 상태*/
        binding.customCalendar.setOnDateChangedListener { widget, date, selected ->
            hideOrShowButton(date)//날짜를 선택 시 보여주거나 숨김
            getDate(date)//선택한 날짜 출력
            getAllTodo()//선택된 날짜의 일정을 출력
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




    //설명 : 날짜를 클릭하여 그 날짜에 일정을 추가하는 기능
    private fun addTodo(){
        binding.insertTodoBtn.setOnClickListener {
            val dateText = binding.outputDate.text.toString().trim()
            val todoText = binding.editTodo.text.toString().trim()
            try{
                if(TextUtils.isEmpty(dateText)||TextUtils.isEmpty(todoText)){
                    Toast.makeText(this,"날짜 선택 또는 일정을 입력하세요",Toast.LENGTH_SHORT).show()
                }else{
                    todo = Todo(dateText,todoText)
                    databaseHelper.insertData(todo!!)
                    clearEditTexts()
                    Toast.makeText(this,"일정이 추가됨",Toast.LENGTH_SHORT).show()
                }
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    //설명 : 날짜를 누르고 id와 일정을 입력하면 기존 일정이 변경됨
    private fun updateTodo(){
        binding.updateTodoBtn.setOnClickListener {
            val dateText = binding.editID.toString().trim()
            val todoText = binding.editTodo.toString().trim()
            todo = Todo(dateText, todoText)
            try{
                databaseHelper.updateData(todo!!)
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
        val dateText = binding.outputDate.text.toString().trim()
        todo = Todo(dateText,null)
        try{
            val selectResult = databaseHelper.getAllData(todo!!)
            showTodo(selectResult)
        }catch(e: Exception){
            e.printStackTrace()
        }
    }
}