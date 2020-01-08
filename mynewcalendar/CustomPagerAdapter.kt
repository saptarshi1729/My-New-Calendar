package com.example.mynewcalendar

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.*

public class CustomPagerAdapter(var context: Context, var fm:FragmentManager) : FragmentStatePagerAdapter(fm){

    override fun getItem(p0: Int): Fragment {
//        var p = p0+60
        var p=p0
        var month = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH)
        var year = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR)
        year = year + (p/12) - 5
        month = month + p - (p/12)*12
        if(month>11){
            month-=12
            year=year+1
        }
        else if(month<0){
            month+=12
            year=year-1
        }

        return FragmentViewPager.newInstance(month,year,context)
//        when(p0){
//
//
//            0->return FragmentViewPager.newInstance(0,context)
//            1->return FragmentViewPager.newInstance(1,context)
//            2->return FragmentViewPager.newInstance(2,context)
//            3->return FragmentViewPager.newInstance(3,context)
//            4->return FragmentViewPager.newInstance(4,context)
//            else->return FragmentViewPager.newInstance(p0%12,context)
//        }
    }

    override fun getCount(): Int {
        return 200
    }

}