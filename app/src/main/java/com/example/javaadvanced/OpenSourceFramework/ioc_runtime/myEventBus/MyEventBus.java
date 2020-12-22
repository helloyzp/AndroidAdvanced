package com.example.javaadvanced.OpenSourceFramework.ioc_runtime.myEventBus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 手写MyEventBus，实现EventBus的核心原理
 */
public class MyEventBus {

    static volatile MyEventBus defaultInstance;

    /**
     * 保存所有的订阅方法，key是订阅者，value是订阅者里面的所有标注了Subscribe注解的方法
     */
    private Map<Object, List<SubscriberMethod>> mAllSubscriberMethods;

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    private final ExecutorService DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private final ExecutorService executorService = DEFAULT_EXECUTOR_SERVICE;

    private MyEventBus() {
        mAllSubscriberMethods = new HashMap<>();
    }


    public static MyEventBus getDefault() {
        MyEventBus instance = defaultInstance;
        if (instance == null) {
            synchronized (MyEventBus.class) {
                instance = MyEventBus.defaultInstance;
                if (instance == null) {
                    instance = MyEventBus.defaultInstance = new MyEventBus();
                }
            }
        }
        return instance;
    }

    /**
     * 供订阅者调用
     *
     * @param subscriber
     */
    public synchronized void register(Object subscriber) {
        List<SubscriberMethod> methodList = mAllSubscriberMethods.get(subscriber);
        if (methodList == null) {
            Class<?> clazz = subscriber.getClass();
            methodList = new ArrayList<>();
            /**
             * 有些情况下可能我们继承了父类，但父类并没有注册，只提供了订阅方法，而是让子类去注册，
             * 那么就需要将父类的订阅方法也保存起来
             */
            while (clazz != null) {
                String className = clazz.getName();
                if (className.startsWith("java.") || className.startsWith("javax.")
                        || className.startsWith("android.")) {
                    break;
                }
                findAnnotationMethod(methodList, clazz);
                clazz = clazz.getSuperclass();
            }
            mAllSubscriberMethods.put(subscriber, methodList);
        }

    }

    /**
     * 寻找使用了Subscribe注解修饰的方法
     *
     * @param methodList
     * @param clazz
     */
    private void findAnnotationMethod(List<SubscriberMethod> methodList, Class<?> clazz) {
        //获取订阅者自身的所有方法，用getDeclaredMethods()，而getMethod()会将父类的方法也获取到
        Method[] m = clazz.getDeclaredMethods();
        int size = m.length;
        for (int i = 0; i < size; i++) {
            Method method = m[i];
            //拿到该方法的注解，找到使用Subscribe注解的方法
            Subscribe annotation = method.getAnnotation(Subscribe.class);
            if (annotation == null)
                continue;

            /**
             * 到这里说明该方法使用了我们定义的Subscribe注解
             * 接下来需要判断该注解了的方法是否符合规范：
             * 1. 返回值必须是void
             * 2. 方法修饰符必须是public，且是非静态抽象的
             * 3. 方法参数必须只有一个
             */
            //如果方法返回类型不是void 抛出异常
            Type genericReturnType = method.getGenericReturnType();
            if (!"void".equals(genericReturnType.toString())) {
                throw new MyEventBusException("方法返回值必须是void");
            }
            //如果方法修饰符不是public 抛出异常
            int modifiers = method.getModifiers();
            if ((modifiers & Modifier.PUBLIC) != 1) {
                throw new MyEventBusException("方法修饰符必须是public，且是非静态，非抽象");
            }
            //如果方法参数不是一个 抛出异常
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new MyEventBusException("方法参数个数必须是一个");
            }

            //实例化订阅方法对象
            SubscriberMethod subscriptionMethod = new SubscriberMethod(method, annotation.threadMode(), parameterTypes[0]);
            methodList.add(subscriptionMethod);
        }
    }

    public synchronized void unregister(Object subscriber) {

      /*  List<SubscriberMethod> methodList = mAllSubscriberMethods.get(subscriber);
        if (methodList == null)
            return;
        methodList.clear();
        mAllSubscriberMethods.remove(subscriber);*/
    }

    /**
     * 发布事件的方法
     * @param event
     */
/*    public void post(Object event){

        Set<Object> set = mAllSubscriberMethods.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            List<SubscriberMethod> methodList = mAllSubscriberMethods.get(next);
            if (methodList == null || mAllSubscriberMethods.size() == 0) {
                continue;
            }
            int size = methodList.size();
            for (int i = 0; i < size; i++) {
                SubscriberMethod method = methodList.get(i);
                //method.getEventType()是获取方法参数类型，这里是判断发布的对象类型是否与订阅方法的参数类型一致
                if (method.getEventType().isAssignableFrom(event.getClass())) {
                    invoke(next,method,event);
                }
            }
        }

    }*/


    /**
     * 发布事件的方法
     *
     * @param event
     */
    public void post(final Object event) {

        Set<Object> set = mAllSubscriberMethods.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            List<SubscriberMethod> methodList = mAllSubscriberMethods.get(next);
            if (methodList == null || mAllSubscriberMethods.size() == 0) {
                continue;
            }
            int size = methodList.size();
            for (int i = 0; i < size; i++) {
                final SubscriberMethod method = methodList.get(i);
                //method.getEventType()是获取方法参数类型，这里是判断发布的对象类型是否与订阅方法的参数类型一致
                if (method.getEventType().isAssignableFrom(event.getClass())) {
                    //进行线程切换
                    switch (method.getThreadMode()) {
                        case POSTING:
                            invoke(next, method, event);
                            break;
                        case MAIN:
                            //通过Looper判断当前线程是否是主线程
                            if (Looper.getMainLooper() == Looper.myLooper()) {
                                invoke(next, method, event);
                            } else {
                                mMainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(next, method, event);
                                    }
                                });
                            }
                            break;
                        case BACKGROUND:
                            if (Looper.getMainLooper() == Looper.myLooper()) {
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(next, method, event);
                                    }
                                });
                            } else {
                                invoke(next, method, event);
                            }
                            break;
                        case ASYNC:
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    invoke(next, method, event);
                                }
                            });
                            break;
                    }

                }
            }
        }

    }

    private void invoke(Object next, SubscriberMethod method, Object event) {

        Method m = method.getMethod();
        try {
            m.invoke(next, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
