package com.simple.cat.app.dagger.module

import com.simple.cat.app.server.RetrofitApiGenerator
import com.simple.cat.app.server.api.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    internal fun provideDeviceLogsApi(apiGenerator: RetrofitApiGenerator) = apiGenerator.createService(KittyApi::class.java)
}
