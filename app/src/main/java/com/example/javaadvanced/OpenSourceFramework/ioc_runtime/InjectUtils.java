package com.example.javaadvanced.OpenSourceFramework.ioc_runtime;

import android.view.View;

import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.annotations.ContentView;
import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.annotations.EventBase;
import com.example.javaadvanced.OpenSourceFramework.ioc_runtime.annotations.ViewInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectUtils {
    public static void inject(Object context) {
        //布局的注入
        injectLayout(context);
        //控件的注入
        injectView(context);
        //事件的注入 建议使用injectEvent
        injectClick(context);

    }

    private static void injectClick(Object context) {
        //需要一次性处理安卓中23种事件
        Class<?> clazz = context.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            //注意，别把代码写死了 method.getAnnotation(OnClick.class);
            //调用method.getAnnotations()得到方法上所有的注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                //annotation是事件，比如onClick、OnLongClick ，取对应的注解
                Class<?> annotationClass = annotation.annotationType();
                EventBase eventBase = annotationClass.getAnnotation(EventBase.class);
                //如果没有eventBase，则表示当前方法不是一个事件处理的方法
                if (eventBase == null) {
                    continue;
                }
                //否则就是一个事件处理的方法
                //开始获取事件处理的相关信息（三要素）
                //用于确定是哪种事件
//                btn.setOnClickListener（new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
                //1.订阅关系，如：setOnClickListener()方法
//                String listenerSetter();
                String listenerSetter = eventBase.listenerSetter();
                //2.订阅关的事件本身， 如:new View.OnClickListener()
//                Class<?> listenerType();
                Class<?> listenerType = eventBase.listenerType();
                //3.事件处理方法，如：onClick()方法
//                String callbackMethod();
                String callBackMethod = eventBase.callbackMethod();

                //得到3要素之后，就可以执行代码了
                Method valueMethod = null;
                try {
                    //反射调用OnClick、OnLongClick注解的value()方法，得到所有view的id,再根据id得到对应的VIEW（Button）
                    valueMethod = annotationClass.getDeclaredMethod("value");
                    int[] viewId = (int[]) valueMethod.invoke(annotation);
                    for (int id : viewId) {
                        //为了得到Button对象,使用findViewById
                        Method findViewByIdMethod = clazz.getMethod("findViewById", int.class);
                        View view = (View) findViewByIdMethod.invoke(context, id);
                        //运行到这里，view就相当于Activity中我们写的Button
                        if (view == null) {
                            continue;
                        }

                        //activity==context    click===method
                        ListenerInvocationHandler listenerInvocationHandler =
                                new ListenerInvocationHandler(context, method);

                        //做代理   new View.OnClickListener()对象
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader()
                                , new Class[]{listenerType}, listenerInvocationHandler);
                        //执行  让proxy执行的onClick()
                        //参数1  setOnClickListener()
                        //参数2  new View.OnClickListener()对象
                        //view.setOnClickListener(new View.OnClickListener())
                        //获取view对象的setOnClickListener方法，方法的形参是OnClickListener
                        Method setOnClickListeneMethod = view.getClass().getMethod(listenerSetter, listenerType);
                        setOnClickListeneMethod.invoke(view, proxy);//执行view对象的setOnClickListener方法，传递的实参是proxy
                        // 那么当系统调用view设置的监听器的所有方法时(自然包括onClick()、onLongClick())，都会被转为调用proxy的invoke方法()。
                        // 这时候，如果点击按钮，就会去执行代理类proxy中的invoke方法()，最终调用activity中标注了注解的方法

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private static void injectView(Object context) {
        Class<?> clazz = context.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int valueId = viewInject.value();
                //运行到这里，每个按钮的ID已经取到了
                //注入就是反射执行findViewById方法
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    //View view = mainActivity.findViewById(valueId);
                    View view = (View) method.invoke(context, valueId);
                    field.setAccessible(true);
                    //mainActivity的field字段的值设置为view
                    field.set(context, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void injectLayout(Object context) {
        int layoutId = 0;
        Class<?> clazz = context.getClass();
        //接下来会在clazz上面去执行setContentView
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            //取到注解括号里面的内容
            layoutId = contentView.value();
            //反射去执行setContentView
            try {
                Method method = context.getClass().getMethod("setContentView", int.class);
                //要执行context.method(layoutId); 用反射如何实现
                method.invoke(context, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
