package com.example.customglide.load_data;

import android.content.Context;

import com.example.customglide.resource.Value;

/**
 * 加载外部资源 标准制定
 */
public interface ILoadData {

    // 加载外部资源的行为
    Value loadResource(String path, ResponseListener responseListener, Context context);

}
