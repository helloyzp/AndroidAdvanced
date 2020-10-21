package com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.proxymodel.proxy;

/**
 * Created by Alvin on 2017/4/18.
 */

public interface ICallBack{

    public void onFailure(String e);

    public void onSuccess(String result);

}
