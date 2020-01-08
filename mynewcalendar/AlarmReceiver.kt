package com.example.mynewcalendar

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var MID = 1

        var when1 = System.currentTimeMillis()
        var notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var calendar = Calendar.getInstance(TimeZone.getDefault())
//        Log.e("Sap","${calendar.get(Calendar.YEAR)}${calendar.get(Calendar.MONTH)}${calendar.get(Calendar.DATE)}")
        var date = "${calendar.get(Calendar.YEAR)}${calendar.get(Calendar.MONTH)}${calendar.get(Calendar.DATE)}"
        var db = DatabaseHandler(context!!)

        Log.e("Sap","QQQQ")
        val notificationIntent = Intent(context, NotifyActivity::class.java)
        var  b = Bundle()
        b.putStringArrayList("ABC",db.displayTasks(date))
        notificationIntent.putExtra("AB", b)

        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        var pendingIntent = PendingIntent.getActivity(
            context, 0,
            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )


//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        var mNotifyBuilder = Notification.Builder(context).setSmallIcon(R.drawable.index)
            .setContentTitle("Tasks Pending Today")
            .setContentText("Notification")
            .setAutoCancel(true).setWhen(when1)
            .setContentIntent(pendingIntent)
        notificationManager.notify(MID, mNotifyBuilder.build())
        MID++;
    }

}
