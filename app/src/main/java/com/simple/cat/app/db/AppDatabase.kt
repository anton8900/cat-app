package com.simple.cat.app.db

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
object AppDatabase {
    const val NAME = "CatDatabase"
    const val VERSION = 1
}
