package com.example.fragmentdemo.basic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentdemo.LifeCycleFragment
import com.example.fragmentdemo.R

class LeftFragment:LifeCycleFragment() {
    //这是静态fragment的使用方式

    //嵌套了fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_left,container,false)
    }

    /**
     *     android:onClick="showMsg"指定的方法必须定义在Activity中，不能定义在Fragment中，
     *     因为view会去所属的context中寻找对应的方法，而Fragment不是context
     */
    fun showMsg(view: View) {
        Log.i(TAG,"show left fragment")
    }
}