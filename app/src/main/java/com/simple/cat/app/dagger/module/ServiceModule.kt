package com.simple.cat.app.dagger.module

import android.app.Application
import com.simple.cat.app.repository.kitty.IKittyRepository
import com.simple.cat.app.service.SettingsService
import com.simple.cat.app.service.kitty.KittyService
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by SergeyD on 11.08.2017.
 */
@Module
class ServiceModule {
    @Provides
    @Singleton
    internal fun provideSettingsService(application: Application) = SettingsService(application)

    //thread pool for apps background tasks
    @Provides
    @Singleton
    @Named("otherWorkThreadPool")
    fun provideOtherWorkScheduledExecutorService(): ScheduledExecutorService {
        return Executors.newScheduledThreadPool(3)
    }

    @Provides
    @Singleton
    fun provideCatService(kittyRepository: IKittyRepository) = KittyService(kittyRepository)
}