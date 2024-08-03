package com.example.android.di

import com.example.android.di.module.AppModule
import com.example.external.di.LocalDbModule
import com.example.external.di.RepositoriesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        RepositoriesModule::class,
        LocalDbModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: android.app.Application): Builder

        fun localDbModule(localDbModule: LocalDbModule): Builder

        fun build(): AppComponent
    }

    fun inject(application: com.example.android.Application)
}
