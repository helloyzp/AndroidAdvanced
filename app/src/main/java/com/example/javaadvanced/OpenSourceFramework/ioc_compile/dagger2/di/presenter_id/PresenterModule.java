package com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.di.presenter_id;


import javax.inject.Singleton;

import androidx.core.view.ViewCompat;

import com.example.javaadvanced.OpenSourceFramework.ioc_compile.dagger2.scope.Userscope;

import dagger.Module;
import dagger.Provides;
@Userscope
@Module
public class PresenterModule {
    @Userscope
    @Provides
    public Presenter providePresenter(){
        return new Presenter();
    }
}
