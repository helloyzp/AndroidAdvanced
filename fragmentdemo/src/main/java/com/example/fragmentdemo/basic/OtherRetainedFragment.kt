package com.example.fragmentdemo.basic

import android.os.Bundle
import com.example.fragmentdemo.LifeCycleFragment

class OtherRetainedFragment:LifeCycleFragment() {

    var data: MyAsyncTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

}