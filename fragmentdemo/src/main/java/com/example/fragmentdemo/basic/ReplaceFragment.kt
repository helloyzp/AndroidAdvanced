package com.example.fragmentdemo.basic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fragmentdemo.LifeCycleFragment
import com.example.fragmentdemo.R
import org.jetbrains.anko.support.v4.find

class ReplaceFragment:LifeCycleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_num,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = find<TextView>(R.id.text)
        val num = arguments?.getString("NAME","null")
        text.text = String.format(getString(R.string.fragment_text),num)
    }

    companion object{
        fun newInstance(num: Int): Fragment {
            val fragment = ReplaceFragment()
            val bundle = Bundle()
            bundle.putString("NAME",num.toString())
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(name: String): Fragment {
            val fragment = ReplaceFragment()
            val bundle = Bundle()
            bundle.putString("NAME",name)
            fragment.arguments = bundle
            return fragment
        }
    }

}