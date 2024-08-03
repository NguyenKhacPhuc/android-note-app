package com.example.external.setup

interface DbConnectionFactory<T> {

    val databaseConnection: T
}
