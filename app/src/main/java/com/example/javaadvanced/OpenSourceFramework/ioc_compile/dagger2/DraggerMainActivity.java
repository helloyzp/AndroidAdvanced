package com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.javaadvanced.MyApplication;
import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.di.presenter_id.DaggerPresenterComponent;
import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.di.presenter_id.Presenter;
import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.object.DatabaseObject;
import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.object.HttpObject;
import com.example.javaadvanced.R;

import javax.inject.Inject;

/**
 * dragger的基本使用：
 * 使用dragger注入对象
 */
public class DraggerMainActivity extends AppCompatActivity {
    private String TAG = "DraggerMainActivity";

    /**
     * 以前需要用到httpObject对象都是使用httpObject = new HttpObject()，进行主动的new一个对象，
     * 使用了dragger后进行注入即可。
     */
    @Inject
    HttpObject httpObject;//=new HttpObject();
    @Inject
    HttpObject httpObject2;//=new HttpObject();
    @Inject
    DatabaseObject databaseObject;//=new databaseObject();

    @Inject
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragger_main);

        //Dragger有个不好的地方就是还有要写类似下面的注入代码，daggerAndroid则不需要，可以参考下daggerAndroid是如何做的。
        //DaggerMyComponent.create().injectMainActivity(this);
/*        DaggerMyComponent.builder()
                .httpModule(new HttpModule())
                .databaseModule(new DatabaseModule())
                .presenterComponent(DaggerPresenterComponent.create())
                .build() //到这里就已经在内存中初始化出了module和component
                .injectMainActivity(this);*/

        ((MyApplication) getApplication()).getAppComponent().injectMainActivity(this);

        Log.i(TAG, "httpObject=" + httpObject.hashCode() + "");
        Log.i(TAG, "httpObject2=" + httpObject2.hashCode() + "");
        Log.i(TAG, "databaseObject=" + databaseObject.hashCode() + "");
        Log.i(TAG, "presenter=" + presenter.hashCode());
    }

    public void click(View view) {
        startActivity(new Intent(this, DraggerSecondActivity.class));
    }
}
