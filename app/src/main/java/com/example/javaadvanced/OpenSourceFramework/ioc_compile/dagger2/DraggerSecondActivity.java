package com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


import com.example.javaadvanced.MyApplication;
import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.di.presenter_id.Presenter;
import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.object.HttpObject;
import com.example.javaadvanced.R;

import javax.inject.Inject;

public class DraggerSecondActivity extends AppCompatActivity {
    private String TAG = "DraggerSecondActivity";

    @Inject
    HttpObject httpObject;//=new HttpObject();

    @Inject
    Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragger_second);


//        DaggerMyComponent.builder()
//                .httpModule(new HttpModule())
//                .databaseModule(new DatabaseModule())
//                .build()
//                //到这里就已经在内存中初始化出了module和component
//                .injectSecActivity(this);

        ((MyApplication) getApplication()).getAppComponent().injectSecActivity(this);

        Log.i("TAG", httpObject.hashCode() + "--sec");
        Log.i("TAG", presenter.hashCode() + "pre");
    }
}
