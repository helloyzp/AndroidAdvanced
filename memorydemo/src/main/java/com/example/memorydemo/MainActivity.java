package com.example.memorydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.example.memorydemo.heap.MATHeap;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import de.robv.android.xposed.DexposedBridge;
import de.robv.android.xposed.XC_MethodHook;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);

        DexposedBridge.hookAllConstructors(ImageView.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                // 1
                DexposedBridge.findAndHookMethod(ImageView.class, "setImageBitmap", Bitmap.class, new ImageHook());
            }
        });

        matTest();
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(1000*30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                incomingVsoutgoing();
//                shallowRetainedHeapTest();
                createDumpFile(MainActivity.this);
            }
        }.start();

//        matTest();
    }

    void incomingVsoutgoing(){
        A a = new A();
        B b = new B();
    }

    void shallowRetainedHeapTest(){
        MATHeap.A a = new MATHeap.A();
    }

    void matTest(){
        ObjectsMAT objectsMAT = new ObjectsMAT();
        objectsMAT.startZeroThread();

    }


    public static void verifyStoragePermissions(AppCompatActivity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean createDumpFile(Context context) {
        Log.i("Zero","开始dump...");
        String LOG_PATH = "/dump.gc/";
        boolean bool = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ssss");
        String createTime = sdf.format(new Date(System.currentTimeMillis()));
        String state = android.os.Environment.getExternalStorageState();
        // 判断SdCard是否存在并且是可用的
        if(android.os.Environment.MEDIA_MOUNTED.equals(state)){
            File file = new File(Environment.getExternalStorageDirectory().getPath() +LOG_PATH);
            if(!file.exists()) {
                file.mkdirs();
            }
            String hprofPath = file.getAbsolutePath();
            if(!hprofPath.endsWith("/")) {
                hprofPath+= "/";
            }

            hprofPath+= createTime + ".hprof";
            try {
                android.os.Debug.dumpHprofData(hprofPath);
                bool= true;
                Log.d("ANDROID_LAB", "create dumpfile done!");
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bool= false;
            Log.d("ANDROID_LAB", "nosdcard!");
        }

        return bool;
    }
}
