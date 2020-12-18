package com.example.javaadvanced.OpenSourceFramework.ioc_compile.Butterknife.mybutterknife;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.annotations.BindView;
import com.example.javaadvanced.R;

/**
 * 手写实现Butterknife核心原理，编译时注入
 */
public class MyButterknifeActivity extends Activity {

    @BindView(R.id.btn)
    Button btn;

//    @BindView(R.id.btn2)
//    Button btn2;
//
//    @BindView(R.id.btn3)
//    Button btn3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybutterknife);

        MyButterknife.bind(this);

        btn.setText("1234");
    }
}
