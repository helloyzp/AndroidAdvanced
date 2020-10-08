package com.example.javaadvanced;

import android.app.Application;
import android.content.Context;

import com.example.javaadvanced.jvm.AndroidVM.Hotfix;

import java.io.File;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //执行热修复。 插入补丁包dex。冷启动修复。
        // /data/data/xxx/files/xxxx.dex
        // /sdcard/xxx.dex
        // 修复后的类必须在原来的类还没有被加载时进行加载(双亲委托机制，不然加载的还是原来的类)，
        // 所以补丁包在attachBaseContext()方法里进行安装
        Hotfix.installPatch(this,new File("/sdcard/patch.dex"));
    }

}
