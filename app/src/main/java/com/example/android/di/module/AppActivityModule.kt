package com.example.android.di.module

import com.example.android.di.scope.ActivityScoped
import com.example.android.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): MainActivity
}
