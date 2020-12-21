package com.example.javaadvanced.OpenSourceFramework.ioc_runtime.mybutterknife.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)//java-->class-->runtime
@EventBase(listenerSetter = "setOnLongClickListener"
        ,listenerType = View.OnLongClickListener.class
        ,callbackMethod = "onLongClick")
public @interface OnLongClick {
    int[] value() default -1;
}









