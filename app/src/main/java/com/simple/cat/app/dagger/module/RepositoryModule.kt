package com.simple.cat.app.dagger.module

import com.simple.cat.app.repository.kitty.IKittyRepository
import com.simple.cat.app.repository.kitty.DbFlowKittyRepository
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
class RepositoryModule {

    //all operations that change db file should be invoked only on this thread
    //cause two threads can't to write file at the same time, exception can occur
    @Provides
    @Named("DBThread")
    @Singleton
    internal fun provideDBThread(): ScheduledExecutorService = Executors.newScheduledThreadPool(1)

    @Provides
    @Singleton
    internal fun provideTicketTypeRepository(@Named("DBThread") dBThread: ScheduledExecutorService): IKittyRepository = DbFlowKittyRepository(dBThread)
}