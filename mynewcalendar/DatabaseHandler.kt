package com.example.mynewcalendar

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

public class DatabaseHandler(context: Context) : SQLiteOpenHelper(context,"MyDB",null,1) {

    private lateinit var query : String
    private val table = "TASKS"
//    private val table2 = "RECORD"

    override fun onCreate(db: SQLiteDatabase?) {
//        db!!.execSQL("DROP TABLE TASKS;")
        query = "CREATE TABLE IF NOT EXISTS TASKS(ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE String NOT NULL, TASK String NOT NULL);"
        db!!.execSQL(query)
//        query = "CREATE TABLE $table2(DATE, COUNT, PRIMARY KEY(DATE));"
//        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun isThereAnyTask(date: String): Boolean{
        val db=this.readableDatabase
        val cursor =db.rawQuery("SELECT * FROM $table WHERE DATE = $date;",null)
        var flag=false
        if(cursor.moveToFirst()){
            do{
                flag=true
                break
            }while(cursor.moveToNext())
        }
        return flag
    }

    fun addTask(date: String, task: String){


//        var db = this.readableDatabase
//        var count = db!!.execSQL("SELECT COUNT(*) FROM $table WHERE DATE = date")
        var db = this.writableDatabase
//        var values = ContentValues()
//        values.put("DATE",date)
////        values.put("INDEX","${count.toString().toInt()+1}")
//        values.put("TASK",task)
//        var f=db.insert(table,null,values)

//        query = "CREATE TABLE IF NOT EXISTS TASKS(DATE String, TASK String, PRIMARY KEY(DATE, TASK));"
//        db!!.execSQL(query)

//        db!!.execSQL("DROP TABLE TASKS;")
        db!!.execSQL("INSERT INTO TASKS (DATE,TASK) VALUES(\"${date}\",\"${task}\");")
//        db!!.execSQL("INSERT INTO TASKS (DATE,TASK) VALUES(\"523435\",\"gfg\");")
//        Log.e("Sap","$f")
        db.close()
    }

    fun deleteTask(date: String, index: Int){
        val db = this.writableDatabase
        val cursor =db.rawQuery("SELECT * FROM $table WHERE DATE = $date;",null)
        var t=index
        if(cursor.moveToFirst()&&index>0){
            do{
                if(t==1){
                    var ind = cursor.getString(0)
                    db.execSQL("DELETE FROM $table WHERE ID = $ind;")
                    break
                }
                t--
            }while(cursor.moveToNext())
        }
    }

    fun deleteAllTasks(date: String){
        val db= this.writableDatabase
//        query = "SELECT * FROM $table"
//        val cursor = db.rawQuery(query, null)
//        if (cursor.moveToFirst()) {
//            do {
//
//            } while (cursor.moveToNext())
//        }
//        Log.e("Sap1","$date ${db!!.execSQL("SELECT COUNT(*) FROM $table WHERE DATE = $date")}")
        db.execSQL("DELETE FROM $table WHERE DATE = $date;")
        db.close()
    }

    fun displayTasks(date: String): ArrayList<String>{
        val db=this.readableDatabase
        val cursor =db.rawQuery("SELECT * FROM $table WHERE DATE = $date;",null)
        //Log.e("Sap1","$date ${db.rawQuery("SELECT COUNT(*) FROM $table WHERE DATE = $date;",null)}")
        var tasks = ArrayList<String>()
        if(cursor.moveToFirst()){
            do{
                tasks.add(cursor.getString(2))
            }while(cursor.moveToNext())
        }
        return tasks
    }

}