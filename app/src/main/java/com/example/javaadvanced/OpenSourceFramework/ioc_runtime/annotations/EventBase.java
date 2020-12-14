package com.example.javaadvanced.OpenSourceFramework.ioc_runtime.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)//ANNOTATION_TYPE表示这个注解是用在其他注解上面的，比如写在OnClick注解上
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
//    textView.setOnClickListener（new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    });
    //1.订阅关系，如：setOnClickListener()方法，是一个字符串，代表方法名setOnClickListener
    String listenerSetter();
    //2.订阅关的事件本身， 如:new View.OnClickListener()，是一个calss对象，View.OnClickListener.class
    Class<?> listenerType();
    //3.事件处理方法，如：onClick()方法，是一个字符串，代表方法名onClick
    String callbackMethod();

}









