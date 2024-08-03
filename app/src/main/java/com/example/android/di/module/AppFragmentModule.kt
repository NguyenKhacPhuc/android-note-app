package com.example.android.di.module

import com.example.android.di.scope.FragmentScoped
import com.example.android.ui.EditFragment
import com.example.android.ui.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AppFragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeEditFragment(): EditFragment
}
