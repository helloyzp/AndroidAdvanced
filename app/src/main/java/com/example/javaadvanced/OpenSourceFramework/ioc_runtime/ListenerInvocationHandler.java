package com.example.javaadvanced.OpenSourceFramework.ioc_runtime;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 这个类用来代理new View.OnClickListener()对象
 * 并执行这个对象身上的onClick方法
 */
public class ListenerInvocationHandler implements InvocationHandler {

    //需要在onClick中执行activity.click();
    private Object activity;
    private Method activityMethod;

    public ListenerInvocationHandler(Object activity, Method activityMethod) {
        this.activity = activity;
        this.activityMethod = activityMethod;
    }

    /**
     * 就表示onClick的执行
     * 程序执行onClick方法，就会转到这里来
     * 因为框架中不直接执行onClick
     * 所以在框架中必然有个地方让invoke和onClick关联上
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在这里去调用被注解了的click();
        return activityMethod.invoke(activity, args);
    }


}



