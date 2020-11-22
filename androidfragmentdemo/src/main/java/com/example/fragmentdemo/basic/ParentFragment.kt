package com.example.fragmentdemo.basic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.fragmentdemo.LifeCycleFragment
import com.example.fragmentdemo.R
import org.jetbrains.anko.support.v4.find

class ParentFragment : LifeCycleFragment(),View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_parent, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = find<Button>(R.id.button)
        button.setOnClickListener(this)
    }

    companion object {
        var count = 0
        fun newInstance(): Fragment {
            return ParentFragment()
        }
    }

    override fun onClick(v: View?) {
        childFragmentManager.commit {
            add(getFrameLayoutId(count),ChildFragment.newInstance(count),count.toString())
            addToBackStack(count.toString())
        }
        count++
        if(count > 2){
            count = 0
        }
    }

    private fun getFrameLayoutId(count: Int): Int {
        return when (count) {
            0 -> R.id.child_frameLayout0
            1 -> R.id.child_frameLayout1
            2 -> R.id.child_frameLayout2
            else -> R.id.child_frameLayout0
        }
    }


}