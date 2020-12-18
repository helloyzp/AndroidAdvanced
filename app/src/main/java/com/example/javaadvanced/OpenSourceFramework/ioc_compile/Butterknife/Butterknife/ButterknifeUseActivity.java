package com.example.javaadvanced.OpenSourceFramework.ioc_compile.Butterknife.Butterknife;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javaadvanced.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Butterknife的基本使用
 */
public class ButterknifeUseActivity extends Activity {

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.edittext)
    EditText edittext;

    @OnClick(R.id.button)
    public void onClick() {
        Toast.makeText(this,edittext.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterknife_use);
        ButterKnife.bind(this);

    }

}
