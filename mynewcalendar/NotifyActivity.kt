package com.example.mynewcalendar

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.reminder.*

class NotifyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reminder)
        text.text=display(intent.getBundleExtra("AB").getStringArrayList("ABC"))
    }

    fun display(list: ArrayList<String>) : String {
        var st = ""
        for (i in list){
            st+=i+"\n"
        }
        return st
    }

}
