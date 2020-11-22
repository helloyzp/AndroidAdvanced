package com.example.fragmentdemo.basic

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.fragmentdemo.R

class Retained1DemoActivity: AppCompatActivity() {

     private lateinit var dataFragment: Retained1Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
        Log.i("Zero","${this::class.simpleName} onCreate")
        dataFragment =supportFragmentManager.findFragmentByTag("data") as Retained1Fragment
        if(dataFragment == null){
            dataFragment = Retained1Fragment()
            supportFragmentManager.commit {
                add(dataFragment,"data")
            }
            dataFragment.data = "fsdf"
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i("Zero","${this::class.simpleName} onConfigurationChanged")
    }

    override fun onDestroy() {
        super.onDestroy()
        dataFragment.data = "fsdf"
    }

    fun onShow(view: View) {

        Log.i("Zero","data = ${dataFragment.data}")

    }


}