package com.example.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper private constructor(context : Context) :
    SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){
    companion object{
        //DB 이름, DB 버전, 테이블 이름
        const val DATABASE_NAME = "PlanList.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "plan_table"

        //DB에 저장할 데이터 속성
        const val COL1_ID = "_ID"
        const val COL2_DATETIME = "_DATETIME"
        const val COL3_PLAN = "_PLAN" //나중에 ArrayList로 변경



        //DB에 DBHelper인스턴스가 여러개 접근하면 문제가 발생할 수 있으므로 이를 방지하기 위해 싱글톤 패턴을 사용함
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

    //시스템에 데이터 베이스 파일이 없을 경우에만 단 1번 실행되는 메소드
    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery : String = "CREATE TABLE $TABLE_NAME (" +
                "$COL1_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL2_DATETIME TEXT NOT NULL, " +
                "$COL3_PLAN TEXT NOT NULL" +
                ")"

        db?.execSQL(createQuery)
    }


    //DB버전이 변경되었을때 실행되는 메소드
    /*기능 : DB버전이 변경되었으면 기존 DB를 삭제하고 새로운 DB를 작성함*/
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion){
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME;")
            onCreate(db)
        }
    }


    //기능 : INSERT문을 사용해서 테이블에 일정을 추가한다.
    fun insertData(date: String, plan: String){
        val db : SQLiteDatabase = this.writableDatabase
        //('와 ')은 특수문자(와 )를 사용한것이다.(영상 27:12 참고)
        val insertQuery: String = "INSERT INTO $TABLE_NAME($COL2_DATETIME,$COL3_PLAN) VALUES('$date','$plan');"
        db.execSQL(insertQuery)
    }


    /*기능 : id를 고유키로 갖고있는 데이터를 갱신*/
    fun updateData(id: String, plan: String){
        val db : SQLiteDatabase = this.writableDatabase
        val updateQuery: String = "UPDATE $TABLE_NAME SET $COL3_PLAN = '$plan' WHERE $COL1_ID = '$id';"
        db.execSQL(updateQuery)
    }


    /*기능 : id를 고유키로 갖고있는 데이터를 삭제*/
    fun deleteData(id: String){
        val db: SQLiteDatabase = this.writableDatabase
        val deleteQuery: String ="DELETE FROM $TABLE_NAME WHERE $COL1_ID = '$id';"
        db.execSQL(deleteQuery)
    }


    /*기능 : date와 일치하는 데이터를 가져옴*/
    fun getAllData(date: String) : String{
        var result = "No data in DB"
        val db : SQLiteDatabase = this.readableDatabase
        //WHERE절에서 SQLite의 date함수를 호출해서 사용하도록 쿼리문을 구성하려고함
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE date($COL2_DATETIME) = '$date';", null)

        try{
            if(cursor.count != 0){
                val stringBuffer = StringBuffer()
                while(cursor.moveToNext()){
                    stringBuffer.append("ID : " + cursor.getInt(0).toString()+"\n")
                    stringBuffer.append("날짜 : " + cursor.getString(1).toString()+"\n")
                    stringBuffer.append("일정 : " + cursor.getString(2).toString()+"\n\n")
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