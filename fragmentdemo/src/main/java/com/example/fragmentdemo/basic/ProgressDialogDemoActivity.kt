package com.example.fragmentdemo.basic

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentdemo.ProgressDialog
import com.example.fragmentdemo.R

class ProgressDialogDemoActivity: AppCompatActivity() {

   private val progressDialog = ProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progressdialog)
    }

    fun showProgress(view: View) {

        progressDialog.show(supportFragmentManager,"show")
    }
    fun hideProgress(view: View) {
        progressDialog.dismiss()
    }
}