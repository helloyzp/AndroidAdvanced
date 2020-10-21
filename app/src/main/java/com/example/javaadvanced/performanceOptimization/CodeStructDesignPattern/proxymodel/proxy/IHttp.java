package com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.proxymodel.proxy;

import java.util.Map;

/**
 * @author 享学课堂 Alvin
 * @package com.xiangxue.alvin.proxymodel.proxy
 * @fileName IHttp
 * @date on 2018/10/18
 * @qq 2464061231
 **/

//行为规范的接口,表示代理类能代理什么事
//代理对象和被代理对象(真实对象)都要实现该接口
public interface IHttp {
    // post
    public void post(String url, Map<String, Object> params, ICallBack callback);

    // get
    public void get(String url, Map<String, Object> params, ICallBack callback);

    // down

    // upload
}
