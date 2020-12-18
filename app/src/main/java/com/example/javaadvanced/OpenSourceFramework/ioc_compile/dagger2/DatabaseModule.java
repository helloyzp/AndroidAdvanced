package com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2;



import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.object.DatabaseObject;

import dagger.Module;
import dagger.Provides;

/**
 * 这个类用来提供对象
 */
@Module
public class DatabaseModule {

    @Provides
    public DatabaseObject providerDatabaseObject(){
        return new DatabaseObject();
    }

    //也可以提供其它的对象
//    @Provides
//    public Object providerObject(){
//        return new Object();
//    }

}










