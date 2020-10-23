package com.example.javaadvanced.performanceOptimization.Bitmap.bigimage;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * 长图加载
 */
public class BitmapBigImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_bigimage);

        BigImageView bigImageView = findViewById(R.id.iv_big);
        InputStream is = null;
        try {
            //is = getAssets().open("bigimage_text.png");
            is = getAssets().open("bigimage_worldmap.jpg");

            bigImageView.setImage(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
