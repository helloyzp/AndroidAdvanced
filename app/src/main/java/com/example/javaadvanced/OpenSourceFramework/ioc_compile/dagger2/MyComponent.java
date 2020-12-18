package com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2;


import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.di.presenter_id.PresenterComponent;
import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.scope.Appscope;

import dagger.Component;

/**
 * scope&dependencies的使用
 * 1.多个component上面的scope不能相同
 * 2.没有scope的组件不能去依赖有scope的组件
 */
@Component(modules = {HttpModule.class, DatabaseModule.class}
        , dependencies = {PresenterComponent.class}
)
@Appscope
public interface MyComponent {

    /**
     * 把MyComponent提供的对象注入到参数activity中，
     * 注意，参数activity这里不能用多态，只能是具体的某个类
     *
     * @param activity
     */
    void injectMainActivity(DraggerMainActivity activity);

    /**
     * 把MyComponent提供的对象注入到activity中，
     * 注意，参数activity这里不能用多态，只能是具体的某个类
     *
     * @param activity
     */
    void injectSecActivity(DraggerSecondActivity activity);
}
