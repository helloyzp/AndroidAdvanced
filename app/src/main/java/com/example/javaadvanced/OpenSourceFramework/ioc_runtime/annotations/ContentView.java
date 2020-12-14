package com.example.javaadvanced.OpenSourceFramework.ioc_runtime.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//ElementType.TYPE表示这个注解是用在类上面的
@Retention(RetentionPolicy.RUNTIME)//三种注解类型：java-->class-->runtime
public @interface ContentView {
    int value();
}









