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

class ChildFragment : LifeCycleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (arguments != null) {
            inflater.inflate(arguments!!.getInt("LayoutId", R.layout.fragment_child_num1), container, false)
        } else {
            inflater.inflate(R.layout.fragment_child_num, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = find<TextView>(R.id.text)
        val num = arguments?.getString("NAME", "null")
        text.text = num
    }

    companion object {
        fun newInstance(num: Int): Fragment {
            val fragment = ChildFragment()
            val bundle = Bundle()
            bundle.putString("NAME", num.toString())
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(layoutId: Int, num: Int): Fragment {
            val fragment = ChildFragment()
            val bundle = Bundle()
            bundle.putString("NAME", num.toString())
            bundle.putInt("LayoutId", layoutId)
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(name: String): Fragment {
            val fragment = ChildFragment()
            val bundle = Bundle()
            bundle.putString("NAME", name)
            fragment.arguments = bundle
            return fragment
        }
    }

}