package com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.di.presenter_id;


import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.DraggerMainActivity;
import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.DraggerSecondActivity;
import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.scope.Userscope;

import javax.inject.Singleton;

import dagger.Component;
@Userscope
@Component(modules = {PresenterModule.class})
public interface PresenterComponent {
    //使用依赖关系，就不再使用这种方式
//   void injectMainActivity(DraggerMainActivity mainActivity);
//   void injectMainActivity(DraggerSecondActivity secActivity);

    public Presenter providePrsenter();

}
