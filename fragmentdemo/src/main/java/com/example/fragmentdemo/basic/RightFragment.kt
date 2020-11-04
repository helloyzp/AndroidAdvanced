package com.example.fragmentdemo.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentdemo.LifeCycleFragment
import com.example.fragmentdemo.R

class RightFragment:LifeCycleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_right,container,false)
    }
}