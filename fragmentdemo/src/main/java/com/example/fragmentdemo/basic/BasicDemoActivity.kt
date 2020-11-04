package com.example.fragmentdemo.basic

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentdemo.LifeCycleFragment
import com.example.fragmentdemo.R

/**
 * Fragment基本使用：静态加载Fragment
 */
class BasicDemoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
        Log.i("Zero","${this::class.simpleName} onCreate")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i("Zero","${this::class.simpleName} onConfigurationChanged")
    }

    fun showMsg(view: View) {
        Log.i(LifeCycleFragment.TAG,"show left fragment")
    }


}