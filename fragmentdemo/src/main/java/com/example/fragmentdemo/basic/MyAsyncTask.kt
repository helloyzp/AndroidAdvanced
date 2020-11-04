package com.example.fragmentdemo.basic

import android.os.AsyncTask
import androidx.fragment.app.FragmentManager
import com.example.fragmentdemo.ProgressDialog
import java.util.*

class MyAsyncTask(private var activity: TaskDemo1Activity?) : AsyncTask<Void?, Void?, Void?>() {
    /**
     * 是否完成
     */
    private var isCompleted = false
    /**
     * 进度框
     */
    private lateinit var mLoadingDialog: ProgressDialog
    var items: List<String>? = null
        private set

    /**
     * 开始时，显示加载框
     */
    override fun onPreExecute() {
        mLoadingDialog = ProgressDialog()
        mLoadingDialog.show(activity!!.supportFragmentManager,"loading")
//        mLoadingDialog.show(activity.supportFragmentManager!!, "LOADING");
    }

    /**
     * 加载完成回调当前的Activity
     */
    override fun onPostExecute(unused: Void?) {
        isCompleted = true
        notifyActivityTaskCompleted()
        if (mLoadingDialog != null) mLoadingDialog!!.dismiss()
    }

    private fun loadingData(): List<String> {
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
        }
        return ArrayList(listOf("fragment 基本使用",
                "fragment动态添加",
                "getActivity == null", "fragment重叠", "add replace的区别",
                "popstack"))
    }

    /**
     * 设置Activity，因为Activity会一直变化
     *
     * @param activity
     */
    fun setActivity(activity: TaskDemo1Activity?) { // 如果上一个Activity销毁，将与上一个Activity绑定的DialogFragment销毁
        if (activity == null) {
            mLoadingDialog.dismiss()
        }
        // 设置为当前的Activity
        this.activity = activity
        // 开启一个与当前Activity绑定的等待框
        if (activity != null && !isCompleted) {
            mLoadingDialog = ProgressDialog()
            mLoadingDialog.show(activity.supportFragmentManager, "loading")
        }
        // 如果完成，通知Activity
        if (isCompleted) {
            notifyActivityTaskCompleted()
        }
    }

    private fun notifyActivityTaskCompleted() {
        if (null != activity) {
              activity?.onTaskCompleted()
        }
    }

    override fun doInBackground(vararg params: Void?): Void? {
        items = loadingData()
        return null
    }

}