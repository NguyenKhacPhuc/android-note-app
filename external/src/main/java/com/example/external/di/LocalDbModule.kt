package com.example.external.di

import android.app.Application
import androidx.room.RoomDatabase
import com.example.external.setup.DbConnectionFactory
import com.example.external.setup.RoomDatabaseConnectionFactory
import dagger.Module
import dagger.Provides

@Module
class LocalDbModule {
    @Provides
    fun dbConnectionFactory(
        application: Application,
    ): DbConnectionFactory<RoomDatabase> {
        return RoomDatabaseConnectionFactory(application)
    }
}