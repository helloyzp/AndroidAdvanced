package com.example.javaadvanced.performanceOptimization.memory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;


public class MemoryMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_main);

        //传递ApplicationContext，而不是传递Activity
        SingletonManager.getInstance().init(getApplicationContext());


    }

    public void jump1(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void jump2(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }

    public void jump3(View view) {
        startActivity(new Intent(this, MemoryLeakActivity.class));
    }


    /**
     * 正确的字符串拼接，避免内存抖动
     * @param values
     * @return
     */
    public String addStr(String[] values) {
        StringBuilder result = new StringBuilder();
        // 10000
        for (int i = 0; i < values.length; i++) {
            result.append(result).append(values[i]);
        }
        return result.toString();
    }


}
