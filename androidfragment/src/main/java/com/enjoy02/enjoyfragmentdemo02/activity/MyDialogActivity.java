package com.enjoy02.enjoyfragmentdemo02.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.enjoy02.enjoyfragmentdemo02.R;
import com.enjoy02.enjoyfragmentdemo02.dialog.MyDialogFragment;
import com.enjoy02.enjoyfragmentdemo02.fragment.Bug5Fragment;


public class MyDialogActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydialog);


    }

    public void showLoginDialog(View view)
    {
        DialogFragment dialog = MyDialogFragment.newIntance();
        dialog.show(getSupportFragmentManager(),"dialog");
    }

}
