package com.example.javaadvanced.performanceOptimization.Bitmap.bitmapcache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaadvanced.R;

public class BitmapCacheActivity extends AppCompatActivity {
    
    private static String TAG = "BitmapCacheActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_cache);

        initView();
        ImageCache.getInstance().init(this, Environment.getExternalStorageDirectory() + "/bitmap");
    }

    /**
     * 初始化 recyclerView
     */
    private void initView() {
        RecyclerView rv = findViewById(R.id.rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        BitmapAdapter bitmapAdapter = new BitmapAdapter(this);
        rv.setAdapter(bitmapAdapter);
    }

}


