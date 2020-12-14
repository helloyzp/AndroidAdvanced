package com.example.javaadvanced.OpenSourceFramework.ioc_runtime.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)//java-->class-->runtime
public @interface ViewInject {
    int value();
}









