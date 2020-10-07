package com.example.javaadvanced.dispatchevent.clickevent;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;

/**
 * View的dispatchTouchEvent方法先执行mOnTouchListener的onTouch()方法，只有onTouch()方法返回false时，
 * 才去执行onTouchEvent()方法，onClick()方法是在onTouchEvent()方法中调用的。
 * 所以只要onTouch()方法返回true，则onClick()方法是不会执行的。
 */
public class ClickEventActivity extends AppCompatActivity {

    private Button btn_click;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clickevent);

        btn_click = findViewById(R.id.btn_click);

        btn_click.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e(TAG, "onTouch: " + event.getAction());

                return true;
            }
        });

        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick");
            }
        });


    }
}