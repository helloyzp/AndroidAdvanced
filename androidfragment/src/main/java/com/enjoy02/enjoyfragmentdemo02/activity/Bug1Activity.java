package com.enjoy02.enjoyfragmentdemo02.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;

import com.enjoy02.enjoyfragmentdemo02.R;
import com.enjoy02.enjoyfragmentdemo02.fragment.Bug111Fragment;

/**
 * TODO: getActivity==null
 */
public class Bug1Activity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("Zero", "onCreate");
        setContentView(R.layout.activity_bug1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frameLayout, Bug111Fragment.newIntance(), Bug111Fragment.class.getName());
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Zero", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Zero", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Zero", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Zero", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Zero", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Zero", "onDestroy");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("Zero", "onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("Zero", "onSaveInstanceState");
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.e("Zero", "onAttachFragment: " + fragment);
    }

}
