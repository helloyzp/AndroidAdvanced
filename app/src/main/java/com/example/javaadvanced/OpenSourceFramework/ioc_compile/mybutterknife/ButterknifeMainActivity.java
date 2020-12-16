package com.example.javaadvanced.OpenSourceFramework.ioc_compile.mybutterknife;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.annotations.BindView;
import com.example.javaadvanced.R;


public class ButterknifeMainActivity extends Activity {

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
        setContentView(R.layout.activity_main_butterknife);

        MyButterknife.bind(this);

        btn.setText("1234");
    }
}
