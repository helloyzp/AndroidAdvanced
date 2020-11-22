package com.example.fragmentdemo

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.example.fragmentdemo.basic.*
import org.jetbrains.anko.support.v4.startActivity

class MainFragment : ListFragment() {
    private lateinit var arrayAdapter: ArrayAdapter<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val array = arrayOf(
                "静态使用",
                "动态使用",
                "保存bitmap",
                "保存Task",
                "Can not perform this action after onSaveInstanceState",
                "Fragment重叠异常",
                "嵌套的fragment不能在onActivityResult()中接收到返回值",
                "未必靠谱的出栈方法remove()",

                "popBackStack的坑",
                "pop多个Fragment时转场动画 带来的问题",
                "进入新的Fragment并立刻关闭当前Fragment 时的一些问题",
                "Fragment+viewpager"
        )
        arrayAdapter = ArrayAdapter(activity!!, R.layout.simple_list_item_1, array)
        listAdapter = arrayAdapter
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) { // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id)
        val item = arrayAdapter.getItem(position)
        Toast.makeText(activity, item, Toast.LENGTH_LONG).show()
        when (position) {
            0 -> {
              startActivity(Intent(activity,BasicDemoActivity::class.java))
            }
            1 -> {
                startActivity<BasicDynamicDemoActivity>()
            }
            2 -> {
                startActivity<BitmapDemoActivity>()
            }
            3 -> {
                startActivity<TaskDemo1Activity>()
            }
            else -> {
            }
        }
    }

    companion object {
        fun newIntance(): Fragment {
            return MainFragment()
        }
    }
}