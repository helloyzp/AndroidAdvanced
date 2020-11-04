package com.example.fragmentdemo

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

interface OnBackPressed{
    fun onBackPressed()
}
open class LifeCycleFragment : Fragment(), OnBackPressed {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG,"${this::class.simpleName} onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG,"${this::class.simpleName} onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG,"${this::class.simpleName} onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG,"${this::class.simpleName} onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG,"${this::class.simpleName} onActivityCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.i(TAG,"${this::class.simpleName} onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG,"${this::class.simpleName} onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG,"${this::class.simpleName} onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG,"${this::class.simpleName} onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG,"${this::class.simpleName} onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG,"${this::class.simpleName} onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"${this::class.simpleName} onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG,"${this::class.simpleName} onDetach")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.i(TAG,"${this::class.simpleName} setUserVisibleHint 已经废弃")
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i(TAG,"${this::class.simpleName} onHiddenChanged")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(TAG,"${this::class.simpleName} onActivityResult")
    }

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        Log.i(TAG,"${this::class.simpleName} onInflate")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG,"${this::class.simpleName} onSaveInstanceState")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i(TAG,"${this::class.simpleName} onConfigurationChanged")
    }

    companion object{
        const val TAG = "Zero"
        fun newIntance(): Fragment {
            return LifeCycleFragment()
        }
    }

    override fun onBackPressed() {
    }
}