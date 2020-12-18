package com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2;



import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.object.HttpObject;
import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.scope.Appscope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 这个类用来提供对象
 */
@Appscope
@Module
public class HttpModule {

    @Appscope
    @Provides
    public HttpObject providerHttpObject(){
        return new HttpObject();
    }
}










