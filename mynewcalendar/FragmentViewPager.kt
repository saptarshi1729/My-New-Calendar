package com.example.mynewcalendar

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.util.*
import kotlin.ConcurrentModificationException

public class FragmentViewPager : Fragment() {

    internal var mContent: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mContent = activity!!.supportFragmentManager.getFragment(savedInstanceState, "myFragmentName")
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (mContent != null)
            activity!!.supportFragmentManager.putFragment(outState, "myFragmentName", mContent!!)
        super.onSaveInstanceState(outState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)
//        var year = 2019
        var year = arguments!!.getInt("year")
        var month = arguments!!.getInt("month")
        v.title1.text= "$month $year"
        v.view1.layoutManager = GridLayoutManager(context1,7)
        v.view1.addItemDecoration(DividerItemDecoration(context1, DividerItemDecoration.HORIZONTAL))
        v.view1.addItemDecoration(DividerItemDecoration(context1, DividerItemDecoration.VERTICAL))
        var curryear = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR)
        var currmonth = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH)
        var currdate = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DATE)
        var date = currdate
//        var month = currmonth
//        var year = curryear

        if(year==curryear&&month==currmonth)
            setView(year,month,date,v)
        else
            setView(year,month,-1, v)
//        prev.setOnClickListener {
//            month = (month + 11)%12
//            year = if (month==11) year-1 else year
//            if(month==currmonth&&year==curryear)
//                setView(year,month,currdate)
//            else
//                setView(year,month,-1)
//        }
//        next.setOnClickListener {
//            month = (month + 13)%12
//            year = if (month==0) year+1 else year
//            if(month==currmonth&&year==curryear)
//                setView(year,month,currdate)
//            else
//                setView(year,month,-1)
//        }
        return v
    }

    fun setView (year: Int, month: Int, date: Int, v:View){
        var numbers = ArrayList<String>()
        var title = arrayOf("SUN","MON","TUE","WED","THU","FRI","SAT")
        var info = CalendarInfo(month,year)
        var days = info.getDaysNo()
        var month1 = (month + 11)%12
        var year1 = if (month1==11) year-1 else year
        var j= info.getDay()-1
        var j1= CalendarInfo(month1,year1).getDaysNo()
        for(k in title)
            numbers.add(k)
        for(k in j downTo 1)
            numbers.add(" ${j1-k+1} ")
        for(i in 1..days)
            numbers.add("$i")
        var next=1
        while(numbers.size%7!=0)
            numbers.add(" ${next++} ")

        v.view1.adapter = MyAdapter(numbers,context1,year,month,date)
        v.title1.text = info.getTitle()
    }

    companion object {
        internal lateinit var context1 : Context
        fun newInstance(month: Int,year: Int,context: Context): FragmentViewPager {

            val f = FragmentViewPager()
            val b = Bundle()
            b.putInt("month", month)
            b.putInt("year",year)
            context1 = context
            f.arguments = b

            return f
        }
    }

}