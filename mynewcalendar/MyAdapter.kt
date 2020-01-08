package com.example.mynewcalendar

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.R.id.edit
import android.app.AlertDialog
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.android.synthetic.main.dialog.view.note
import kotlinx.android.synthetic.main.dialog2.view.*
import kotlinx.android.synthetic.main.dialog3.view.*
import java.util.*
import kotlin.collections.ArrayList


class MyAdapter(val items : ArrayList<String>, val context: Context, val year : Int, val month : Int, val date : Int) : RecyclerView.Adapter<ViewHolder1>() {

    private var col = ArrayList<Int>()
    var db = DatabaseHandler(context)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder1 {
        val view = LayoutInflater.from(context).inflate(R.layout.block,p0,false)
        return ViewHolder1(view)
    }

    override fun onBindViewHolder(p0: ViewHolder1, p1: Int) {
        var index = "$year$month${items[p1]}"
        p0.textView.text = items[p1]
        col.add(0)
        var g=0
        if(items[p1].startsWith(" "))
            p0.textView.setTextColor(Color.GRAY)
        if(items[p1].equals(""+date)) {
            p0.textView.setBackgroundColor(Color.CYAN)
        }
        else
            p0.textView.setBackgroundColor(Color.WHITE)
        if(items[p1][0]>='0'&&items[p1][0]<='9')
            g=1
        if(g==1&&db.isThereAnyTask(index)){
            p0.textView.setBackgroundColor(Color.GREEN)
        }
        p0.textView.setOnClickListener {

            if (g == 1) {

                var dialogBuilder = AlertDialog.Builder(context).create()
                var dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)

                dialogView.save.setOnClickListener {
                    var str = dialogView.note.text.toString().trim()
                    if (str != null && (!str.equals(""))) {
                        db.addTask(index, str)
                        var list1 = db.displayTasks(index)
                        display(list1)
                    }
                    dialogBuilder.dismiss()
                    p0.textView.setBackgroundColor(Color.GREEN)
                }

                dialogView.remove.setOnClickListener {
                    var dialogBuilder2 = AlertDialog.Builder(context).create()
                    var dialogView2 = LayoutInflater.from(context).inflate(R.layout.dialog3, null)
                    dialogView2.ok.setOnClickListener {
                        var pos = dialogView2.note.text.toString().toInt()
                        db.deleteTask(index, pos)
                        var list1 = db.displayTasks(index)
                        display(list1)
                        dialogBuilder2.dismiss()
                        dialogBuilder.dismiss()

                        if (list1.size == 0) {
                            if (items[p1].equals("" + date)) {
                                p0.textView.setBackgroundColor(Color.CYAN)
                            } else
                                p0.textView.setBackgroundColor(Color.WHITE)
                        }

                    }

                    dialogView2.all.setOnClickListener {
                        db.deleteAllTasks(index)
                        var list1 = db.displayTasks(index)
                        display(list1)
                        dialogBuilder2.dismiss()
                        dialogBuilder.dismiss()

                        if (list1.size == 0) {
                            if (items[p1].equals("" + date)) {
                                p0.textView.setBackgroundColor(Color.CYAN)
                            } else
                                p0.textView.setBackgroundColor(Color.WHITE)
                        }
                    }

                    dialogBuilder2.setView(dialogView2)
                    dialogBuilder2.show()


                }

                dialogView.show.setOnClickListener {
                    var list1 = db.displayTasks(index)
                    display(list1)
                    dialogBuilder.dismiss()
                }

                dialogBuilder.setView(dialogView)
                dialogBuilder.show()
            }
        }
    }

    fun display(list1: ArrayList<String>){
        var s = ""
        var count =1
        for(i in list1)
            s+="${count++}) ${i.trim()}\n"
        s=s.trim()
        var dialogBuilder2 = AlertDialog.Builder(context).create()
        var dialogView2 = LayoutInflater.from(context).inflate(R.layout.dialog2,null)
        dialogView2.text.text = s
        dialogView2.button.setOnClickListener {
            dialogBuilder2.dismiss()
        }
        dialogBuilder2.setView(dialogView2)
        dialogBuilder2.show()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder1 (view: View) : RecyclerView.ViewHolder(view) {
    var textView = view.findViewById<TextView>(R.id.`val`)
}