package com.example.javaadvanced.OpenSourceFramework.ioc_runtime;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.annotations.ContentView;
import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.annotations.OnClick;
import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.annotations.OnLongClick;
import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.annotations.ViewInject;
import com.example.javaadvanced.R;

/**
 *
 *
 * 普通加载布局文件的方式：
 *  在Activity的onCreate()方法写 setContentView(R.layout.activity_main); 主动加载布局文件
 *
 * 使用IOC进行加载布局文件的方式：
 * 调用其他方法让其他方法进行加载，比如调用InjectUtils.inject(this);
 *
 *
 */
@ContentView(R.layout.activity_main_ioc)  //ContentView注解标识出需要哪个布局文件
public class IOCMainActivity extends BaseActivity {
    private String TAG = "IOCMainActivity";


    public Button mBtn2;

    @ViewInject(R.id.btn1)
    Button btn1;

    @ViewInject(R.id.btn2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("按钮1");
        btn2.setText("按钮2");
    }

    @Deprecated
    @OnClick({R.id.btn1, R.id.btn2})
    public void click(View view) {
        Toast.makeText(this, "短按下了", Toast.LENGTH_SHORT).show();
    }

    @OnLongClick({R.id.btn2})
    public boolean longClick(View view) {//longClick是方法名，可以随便取，比如xyz(View view)也可以
        Toast.makeText(this, "长按下了", Toast.LENGTH_SHORT).show();
        //Log.i(TAG, "longClick()", new Exception());
        return false;
    }


}
