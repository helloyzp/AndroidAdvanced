package com.example.javaadvanced.OpenSourceFramework.ioc_compile.Butterknife.mybutterknife;

import android.app.Activity;

/**
 * 手写实现Butterknife核心原理，编译时注入
 */
public class MyButterknife {
    public static void bind(Activity activity) {
        String name = activity.getClass().getName() + "_ViewBinding";
        try {
            Class<?> aClass = Class.forName(name);
            IBinder iBinder = (IBinder) aClass.newInstance();
            iBinder.bind(activity);
        } catch (Exception e) {

        }
    }
}
