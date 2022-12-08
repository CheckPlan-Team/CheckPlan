package com.example.checkplan

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import com.example.DBHelper.DatabaseHelper

//할일 목록을 작성(편집)하기 위한 액티비티

//applicationContext nullpointerException 오류 나는 중...


class InputTodoActivity : AppCompatActivity() {

    val todoEdit = findViewById<EditText>(R.id.TodoEdit)//일정 편집 창
    val timeEdit = findViewById<TimePicker>(R.id.TimeEdit)//시간 편집 창
    val saveBtn = findViewById<Button>(R.id.SaveTodo)//저장 버튼

    private val dbHelper : DatabaseHelper by lazy {
        DatabaseHelper.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_input_todo)

        savedData()
    }

    override fun onDestroy(){
        dbHelper.close()
        super.onDestroy()
    }



    //기능 : 저장 버튼 동작
    /*설명 : 저장 버튼을 누르면 입력된 데이터를 DB에 추가 및 inputTodo 액티비티 종료*/
    fun savedData(){
        //날짜,시간,입력된 플랜을 저장
        saveBtn.setOnClickListener {
            //1.플랜 매니저 프래그먼트에 전달 데이터 전달(액티비티->플래그먼트)


            //2.데이터베이스에 플랜 리스트의 각 요소(플랜 뷰)를 저장
            try{
                dbHelper.insertData(
                    timeEdit.hour.toString().trim() + ":" + timeEdit.minute.toString().trim(),
                    todoEdit.text.toString().trim()
                )
                clearEditTexts()
                showTxt("Data inserted")
            }catch(e : Exception){
                e.printStackTrace()
            }
            finish()
        }

    }

    //기능 : CRUD 조작 결과를 출력
    /*설명 : CRUD 버튼을 통해 조작이 이뤄졌을때 그 결과를 TextView에 표시*/
    private fun showTxt(text : String){
        println(text + "\n")
    }

    //기능 : 편집 창 초기화
    private fun clearEditTexts(){
        todoEdit.setText("")
    }
}










/* 참고할 코드
class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val databaseHelper : DatabaseHelper by lazy{
        DatabaseHelper.getInstance(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        insertDB()
        updateDB()
        deleteDB()
        getAllDB()
    }



    override fun onDestroy(){
        databaseHelper.close()
        super.onDestroy()
    }


    //기능 : CRUD 조작 결과를 출력
    /*설명 : CRUD 버튼을 통해 조작이 이뤄졌을때 그 결과를 TextView에 표시*/
    private fun showTxt(text : String){
        binding.tvResult.append(text + "\n")
    }

    //기능 : EditText 비우기
    /*설명 : CRUD를 위한 버튼 조작을 했을때 EditText에 입력된 내용이 Clear되도록 함*/
    private fun clearEditTexts(){
        with(binding){
            editID.setText("")
            editName.setText("")
            editPhone.setText("")
            editEmail.setText("")
        }
    }

    //기능 : INSERT 버튼 동작
    /*설명 : ID, 이름, 전화번호, 이메일을 입력 후 INSERT 버튼 입력 시 DB에 입력된 데이터가 추가됨*/
    private fun insertDB(){
        binding.btnInsert.setOnClickListener {
            try{
                databaseHelper.insertData(
                    binding.editName.text.toString().trim(),
                    binding.editPhone.text.toString().trim(),
                    binding.editEmail.text.toString().trim()
                )
                clearEditTexts()
                showTxt("Data inserted")
            }catch(e : Exception){
                e.printStackTrace()
            }
        }
    }

    //기능 : UPDATE 버튼 동작
    /*설명 : ID, 이름, 전화번호, 이메일을 입력 후 UPDATE 버튼 입력 시 해당 ID를 고유키로 갖는 데이터를 갱신 */
    private fun updateDB(){
        binding.btnUpdate.setOnClickListener {
            try{
                databaseHelper.updateData(
                    binding.editID.text.toString().trim(),
                    binding.editName.text.toString().trim(),
                    binding.editPhone.text.toString().trim(),
                    binding.editEmail.text.toString().trim()
                )
                clearEditTexts()
                showTxt("Data updated")
            }catch(e : Exception){
                e.printStackTrace()
            }
        }
    }

    //기능 : DELETE 버튼 동작
    /*설명 : ID를 입력 후 DELETE 버튼 입력 시 해당 ID를 고유키로 갖는 데이터를 삭제 */
    private fun deleteDB(){
        binding.btnDelete.setOnClickListener {
            try{
                databaseHelper.deleteData(binding.editID.text.toString().trim())
                clearEditTexts()
                showTxt("Data Deleted")
            }catch(e : Exception){
                e.printStackTrace()
            }
        }
    }

    //기능 : VIEW 버튼 동작
    /*설명 : VIEW 버튼 입력 시 DB의 모든 데이터를 출력*/
    private fun getAllDB(){
        binding.btnView.setOnClickListener {
            try{
                val selectResult : String = databaseHelper.getAllData()
                showTxt(selectResult)
            }catch(e : Exception){
                e.printStackTrace()
            }
        }
    }
}
*/



