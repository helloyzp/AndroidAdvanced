package com.example.fragmentdemo

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

//class MyDialogFragment : DialogFragment() {
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//
////        val  builder = AlertDialog.Builder(activity!!)
////        val inflater = activity.
////
////        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
////        // Get the layout inflater
////        LayoutInflater inflater = getActivity().getLayoutInflater();
////        View view = inflater.inflate(R.layout.dialog_login, null);
////        // Inflate and set the layout for the dialog
////        // Pass null as the parent view because its going in the dialog layout
////        builder.setView(view)
////                // Add action buttons
////                .setPositiveButton("Sign in",
////                        new DialogInterface.OnClickListener()
////                        {
////                            @Override
////                            public void onClick(DialogInterface dialog, int id)
////                            {
////                            }
////                        }).setNegativeButton("Cancel", null);
////        return builder.create();
//
////    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        Log.i(TAG,"onAttach")
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.i(TAG,"onCreate")
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        Log.i(TAG,"onCreateView")
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Log.i(TAG,"onViewCreated")
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        Log.i(TAG,"onActivityCreated")
//    }
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        Log.i(TAG,"onViewStateRestored")
//    }
//
//    override fun onStart() {
//        super.onStart()
//        Log.i(TAG,"onStart")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.i(TAG,"onResume")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.i(TAG,"onPause")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.i(TAG,"onStop")
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        Log.i(TAG,"onDestroyView")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.i(TAG,"onDestroy")
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        Log.i(TAG,"onDetach")
//    }
//
//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        Log.i(TAG,"setUserVisibleHint")
//    }
//
//    override fun onHiddenChanged(hidden: Boolean) {
//        super.onHiddenChanged(hidden)
//        Log.i(TAG,"onHiddenChanged")
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.i(TAG,"onActivityResult")
//    }
//
//    override fun onInflate(activity: Activity, attrs: AttributeSet, savedInstanceState: Bundle?) {
//        super.onInflate(activity, attrs, savedInstanceState)
//        Log.i(TAG,"onInflate")
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        Log.i(TAG,"onSaveInstanceState")
//    }
//
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        Log.i(TAG,"onConfigurationChanged")
//    }
//
//    companion object{
//        const val TAG = "Zero"
//        fun newIntance(): Fragment {
//            return MyDialogFragment()
//        }
//    }
//}