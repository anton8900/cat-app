package com.simple.cat.app.dagger.module

import android.app.Application

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class AppModule(private var application: Application) {
    @Provides
    @Singleton
    internal fun providesApplication(): Application = application
}
