package com.example.fragmentdemo.basic

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.fragmentdemo.R

/**
 * Fragment基本使用：动态加载Fragment
 */
class BasicDynamicDemoActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Zero"
        var count = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_basic)
        count = 0
        Log.i("Zero", "${this::class.simpleName} onCreate")
    }


    fun add(view: View) {
        supportFragmentManager.commit {
            add(getFrameLayoutId(count), NumFragment.newInstance(count), count.toString())
        }
        count++
        if (count > 10) {
            count = 0
        }
    }

    private fun getFrameLayoutId(count: Int): Int {
        return when (count) {
            0 -> R.id.frameLayout0
            1 -> R.id.frameLayout1
            2 -> R.id.frameLayout2
            3 -> R.id.frameLayout3
            4 -> R.id.frameLayout4
            5 -> R.id.frameLayout5
            6 -> R.id.frameLayout6
            7 -> R.id.frameLayout7
            8 -> R.id.frameLayout8
            9 -> R.id.frameLayout9
            10 -> R.id.frameLayout10
            else -> R.id.frameLayout0
        }
    }

    fun replace(view: View) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout0, ParentFragment.newInstance(), "replace")
        }
    }

    fun remove(view: View) {
        supportFragmentManager.commit {
            val fragment = supportFragmentManager.findFragmentByTag(0.toString())
            if (fragment != null && fragment.isAdded) {
                remove(fragment)
            }
        }
        //未必靠谱的出栈方法remove
        Log.i(TAG, "fragments: ${supportFragmentManager.fragments.size}")
    }

    fun pop(view: View) {
        supportFragmentManager.popBackStack()
        count--
        if (count < 0) {
            count = 0
        }
    }

    fun pop1(view: View) {
//        supportFragmentManager.popBackStack(3.toString(),FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.popBackStack(2.toString(), 1)
    }

    fun add1(view: View) {//演示add replace的区别
        supportFragmentManager.commit {
//            hide(supportFragmentManager.findFragmentByTag("0")!!)
            add(R.id.frameLayout0, NumFragment.newInstance(R.layout.fragment_num1, count), "")
        }
    }

    fun add2(view: View) {//添加到回退栈
        supportFragmentManager.commit {
            add(getFrameLayoutId(count), NumFragment.newInstance(count), count.toString())
            addToBackStack(count.toString())
        }
        count++
        if (count > 10) {
            count = 0
        }
    }

    fun getFragments(view: View) {
        Log.i(TAG, "fragments: ${supportFragmentManager.fragments.size}")
    }

    fun replace1(view: View) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout0, ReplaceFragment.newInstance("replace"), "replace")
//            addToBackStack(null)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}