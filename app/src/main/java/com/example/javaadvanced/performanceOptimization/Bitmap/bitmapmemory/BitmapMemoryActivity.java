package com.example.javaadvanced.performanceOptimization.Bitmap.bitmapmemory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;

public class BitmapMemoryActivity  extends AppCompatActivity {

    private static String TAG = "BitmapMemoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_memory);

        decodeBitmap();

    }

    /**
     * 解析图片
     *
     * R.drawable.icon_mv_jpg  大小：49.09KB
     * R.drawable.icon_mv_png  大小：319.93KB
     * R.drawable.icon_mv_webp 大小：12.78KB
     * 三张图片的宽和高都一样(都是300*449)，且放在同一个文件夹，因此
     * BitmapFactory.decodeResource加载三张图片在同一个手机屏幕上的宽和高都是一样的，
     * 而且BitmapFactory.Options配置默认的色彩质量参数都是ARGB_8888，
     * 因此这三张图片载到内存中时占用内存的大小是一样的，虽然这三张图片占用的磁盘大小不一样。
     */
    private void decodeBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_mv_jpg);
        Log.e(TAG, "decodeBitmap1: " + bitmap.getWidth() + "X" + bitmap.getHeight() + "x"
                + bitmap.getConfig() + ",内存总大小" + bitmap.getByteCount());
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_mv_png);
        Log.e(TAG, "decodeBitmap2: " + bitmap1.getWidth() + "X" + bitmap1.getHeight() + "x"
                + bitmap1.getConfig() + ",内存总大小" + bitmap1.getByteCount());
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_mv_webp);
        Log.e(TAG, "decodeBitmap3: " + bitmap2.getWidth() + "X" + bitmap2.getHeight() + "x"
                + bitmap2.getConfig() + ",内存总大小" + bitmap2.getByteCount());
    }

}
