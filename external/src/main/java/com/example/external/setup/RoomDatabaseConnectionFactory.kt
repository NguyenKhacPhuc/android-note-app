package com.example.external.setup

import android.app.Application
import androidx.room.RoomDatabase
import javax.inject.Inject

class RoomDatabaseConnectionFactory @Inject constructor(
    private val application: Application,
) : DbConnectionFactory<RoomDatabase> {

    override val databaseConnection: RoomDatabase
        get() = NotyDatabase.getDatabase(application)
}
