package com.example.customglide.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;

/**
 * 监听Activity的生命周期方法
 *
 * 如果用户App当前的页面是Activity，则使用这个Fragment，
 * 注意这个Fragment是android.app.Fragment(android.app.Fragment是Android SDK里面的)
 */
public class ActivityFragmentManager extends Fragment {

    public ActivityFragmentManager () {
    }

    private LifecycleCallback callback;

    @SuppressLint("ValidFragment")
    public ActivityFragmentManager (LifecycleCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 回调回去
        if (callback != null) {
            callback.glideInitAction();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // 回调回去
        if (callback != null) {
            callback.glideStopAction();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 回调回去
        if (callback != null) {
            callback.glideRecycleAction();
        }
    }
}
