package com.example.javaadvanced.performanceOptimization.Bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 对于OOM异常来说，需要捕获的是OutOfMemoryError，而不是Exception，
 * 如果只捕获Exception，那么是捕获不到的OOM异常的。因为OOM异常对应的是OutOfMemoryError，
 * OutOfMemoryError是一种Error，而不是Exception。
 */
public class BitmapOOM {

    public static void main(String[] args) {
        BitmapOOMTest();
    }

    public static Bitmap BitmapOOMTest() {
        String path = "";//实际开发时的图片路径

        Bitmap defaultBitmapMap = null;//实际开发时初始化一张默认位图

        Bitmap bitmap = null;

        try {
            // 实例化Bitmap
            bitmap = BitmapFactory.decodeFile(path);

        } catch (OutOfMemoryError e) {
            //注意是捕获OutOfMemoryError，而不是捕获Exception，因为OutOfMemoryError是属于Error，而不属于Exception
            //如果只捕获Exception，那么创建Bitmap时如果发生OOM是捕获不到的，从而使应用发生crash。

            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bitmap == null) {
            // 如果bitmap实例化失败，返回默认的Bitmap对象
            return defaultBitmapMap;
        }
        return bitmap;
    }

}
