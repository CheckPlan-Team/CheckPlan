package com.example.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper private constructor(context : Context) :
    SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION){
    companion object{
        //DB 이름, DB 버전, 테이블 이름
        const val DATABASE_NAME = "PlanList.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "plan_table"

        //DB에 저장할 데이터 속성
        const val COL1_ID = "_ID"
        const val COL2_DATETIME = "DATETIME"
        const val COL3_PLAN = "_PLAN" //나중에 ArrayList로 변경



        //싱글톤 패턴을 위해 DatabaseHelper 생성자는 private으로 선언, getInstance내부에서 Double checked Locking을 통해서 인스턴스를 반환
        @Volatile
        private var instance : DatabaseHelper? = null

        fun getInstance(context : Context) =
            instance ?: synchronized(DatabaseHelper::class.java){
                instance ?: DatabaseHelper(context).also{
                    instance = it
                }
            }



    }

    //프로그램에 데이터 베이스 파일이 없을 경우에만 단 1번 실행되는 메소드
    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery : String = "CREATE TABLE $TABLE_NAME (" +
                "$COL1_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL2_DATETIME TEXT, " +
                "$COL3_PLAN TEXT" +
                ")"

        db?.execSQL(createQuery)
    }


    //DB버전이 변경되었을때 실행되는 메소드
    /*기능 : DB버전이 변경되었으면 기존 DB를 삭제하고 새로운 DB를 작성함*/
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion){
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }


    //기능 : 테이블에 데이터 추가
    fun insertData(date: String, plan: String){
        val db : SQLiteDatabase = this.writableDatabase
        val contentValues : ContentValues = ContentValues().apply{
            put(COL2_DATETIME, date)
            put(COL3_PLAN, plan)
        }
        db.insert(TABLE_NAME, null, contentValues)
    }


    /*기능 : id를 고유키로 갖고있는 데이터를 갱신*/
    fun updateData(id: String, date: String, plan: String){
        val db : SQLiteDatabase = this.writableDatabase
        val contentValues : ContentValues = ContentValues().apply {
            put(COL2_DATETIME, date)
            put(COL3_PLAN, plan)
        }

        db.update(TABLE_NAME, contentValues, "$COL1_ID = ?",arrayOf(id))//이 구문 뜻 알아보기
    }

    /*기능 : id를 고유키로 갖고있는 데이터를 삭제*/
    fun deleteData(id: String){
        val db: SQLiteDatabase = this.writableDatabase
        db.delete(TABLE_NAME, "$COL1_ID = ?", arrayOf(id))
    }




    //아직 수정안됨
    /*기능 : 모든 데이터를 출력*/
    fun getAllData() : String{
        var result = "No data in DB"
        val db : SQLiteDatabase = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        try{
            if(cursor.count != 0){
                val stringBuffer = StringBuffer()
                while(cursor.moveToNext()){
                    stringBuffer.append("ID : " + cursor.getInt(0).toString()+"\n")
                    stringBuffer.append("이름 : " + cursor.getString(1).toString()+"\n")
                    stringBuffer.append("전화번호 : " + cursor.getString(2).toString()+"\n")
                    stringBuffer.append("이메일 : " + cursor.getString(3).toString()+"\n\n")
                }
                result = stringBuffer.toString()
            }
        }catch(e : Exception){
            e.printStackTrace()
        }finally{
            if(cursor != null && !cursor.isClosed){
                cursor.close()
            }
        }
        return result
    }
}