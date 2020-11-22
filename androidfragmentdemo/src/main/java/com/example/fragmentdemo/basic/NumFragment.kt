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

class NumFragment @JvmOverloads constructor(val name:String = "", val age: Int = 0) : LifeCycleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (arguments != null) {
            inflater.inflate(arguments!!.getInt("LayoutId", R.layout.fragment_num), container, false)
        } else {
            inflater.inflate(R.layout.fragment_num, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = find<TextView>(R.id.text)
        val num = arguments?.getString("NAME", "null")
        text.text = String.format(getString(R.string.fragment_text), num)
    }

    companion object {
        fun newInstance(num: Int): Fragment {
            val fragment = NumFragment()
            val bundle = Bundle()
            bundle.putString("NAME", num.toString())
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(layoutId: Int, num: Int): Fragment {
            val fragment = NumFragment()
            val bundle = Bundle()
            bundle.putString("NAME", num.toString())
            bundle.putInt("LayoutId", layoutId)
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(name: String): Fragment {
            val fragment = NumFragment()
            val bundle = Bundle()
            bundle.putString("NAME", name)
            fragment.arguments = bundle
            return fragment
        }
    }

}