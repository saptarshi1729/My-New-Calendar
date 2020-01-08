package com.example.mynewcalendar

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter = CustomPagerAdapter(this,supportFragmentManager)
        pager.adapter = adapter
        pager.setPageTransformer(true, ZoomOutPageTransformer())
        pager.setCurrentItem(60)

        val t = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 30)
        calendar.set(Calendar.SECOND, 0)
        if (t <= calendar.getTimeInMillis()) {
            Log.e("Sap","PPPPP")
            //calendar.add(Calendar.DATE, 6); //add 6 days.
            val intent1 = Intent(this,  AlarmReceiver::class.java)
            intent1.action = "NOTE"
            val pendingIntent =
                PendingIntent.getBroadcast(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT)
            val am = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent)
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

    }
}

