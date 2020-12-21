package com.example.javaadvanced.OpenSourceFramework.ioc_runtime.myEventBus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.mybutterknife.BaseActivity;
import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.mybutterknife.annotations.ContentView;
import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.mybutterknife.annotations.OnClick;
import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.mybutterknife.annotations.OnLongClick;
import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.mybutterknife.annotations.ViewInject;
import com.example.javaadvanced.R;


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
