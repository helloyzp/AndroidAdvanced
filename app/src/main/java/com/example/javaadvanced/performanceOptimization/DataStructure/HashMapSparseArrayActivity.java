package com.example.javaadvanced.performanceOptimization.DataStructure;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;

import java.util.HashMap;

public class HashMapSparseArrayActivity extends AppCompatActivity {
    private String TAG = "HashMapSparseArrayActivity";

    private Button btnHashMap;
    private Button btnSparseArray;
    private Button btnTimeuse;

    private HashMap hashMap = new HashMap();
    private SparseArray sparseArray = new SparseArray();

    final int LENGTH = 100000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashmapspasearray);

        btnHashMap = findViewById(R.id.hashmap);
        btnSparseArray = findViewById(R.id.spasearray);
        btnTimeuse = findViewById(R.id.timeuse);

        btnHashMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask() {

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        long starttime = System.currentTimeMillis();
                        for (int i = 0; i < LENGTH; i++) {
                            hashMap.put(i, new byte[10]);
                        }
                        long endtime = System.currentTimeMillis();
                        Log.i(TAG, "hashmap caused: " + (endtime - starttime) + "ms");
                        return null;
                    }
                }.execute();
            }
        });

        btnSparseArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask() {

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        long starttime = System.currentTimeMillis();
                        for (int i = 0; i < LENGTH; i++) {
                            sparseArray.put(i, new byte[10]);
                        }
                        long endtime = System.currentTimeMillis();
                        Log.i(TAG, "sparsearray caused: " + (endtime - starttime) + "ms");
                        return null;
                    }
                }.execute();
            }
        });

        btnTimeuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //时间性能
                timerUseHashMap();
                timerUseSparseArray();
            }
        });
    }


    public void timerUseHashMap() {
        long starttime = System.currentTimeMillis();
        for (int i = 0; i < LENGTH; i++) {
            hashMap.get(i);
        }
        long endtime = System.currentTimeMillis();
        Log.i(TAG, "hashmap caused: " + (endtime - starttime) + "ms");
    }

    public void timerUseSparseArray() {
        long starttime = System.currentTimeMillis();
        for (int i = 0; i < LENGTH; i++) {
            sparseArray.get(i);
        }
        long endtime = System.currentTimeMillis();
        Log.i(TAG, "sparsearray caused: " + (endtime - starttime) + "ms");
    }

}
