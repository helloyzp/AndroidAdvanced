package com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.firstusestruct;

/**
 * Created by Administrator on 2017/5/23.
 */

public interface ICallback {
    void onSuccess(String result);
    void onFailure(String e);
}
