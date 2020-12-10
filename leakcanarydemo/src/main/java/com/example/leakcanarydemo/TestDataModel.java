package com.example.leakcanarydemo;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 单例对象持有Activity的实例，演示内存泄漏
 */
public class TestDataModel {

    private static volatile TestDataModel sInstance = new TestDataModel();

    private List<Activity> activities = new ArrayList<>();

    public static TestDataModel getInstance(){
        return sInstance;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }



}
