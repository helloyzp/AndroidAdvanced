package com.example.javaadvanced.OpenSourceFramework.ioc_runtime.myEventBus;

import java.lang.reflect.Method;

/**
 * 订阅方法的封装类，对订阅方法进行封装
 */
public class SubscriberMethod {
    /**
     * 订阅方法
     */
    final Method method;
    /**
     * 订阅方法的线程模式
     */
    final ThreadMode threadMode;
    /**
     * 订阅方法的参数类型
     */
    final Class<?> eventType;

    public SubscriberMethod(Method method, ThreadMode threadMode, Class<?> eventType) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
    }

    public Method getMethod() {
        return method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public Class<?> getEventType() {
        return eventType;
    }
}
