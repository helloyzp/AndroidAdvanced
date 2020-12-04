package com.example.customglide.fragment;

import androidx.fragment.app.Fragment;


/**
 * 监听FragmentActivity的生命周期方法
 *
 * 如果用户App当前的页面是FragmentActivity，则使用这个Fragment，
 * 注意这个Fragment是androidx.fragment.app.Fragment(androidx.fragment.app.Fragment是扩展包androidx里面的)
 */
public class FragmentActivityFragmentManager extends Fragment {

    public FragmentActivityFragmentManager () {
    }

    private LifecycleCallback callback; // 回调接口

    public FragmentActivityFragmentManager (LifecycleCallback callback) {
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
