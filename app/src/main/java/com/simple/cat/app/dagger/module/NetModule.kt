package com.simple.cat.app.dagger.module

import android.app.Application
import com.simple.cat.app.server.RetrofitApiGenerator
import com.simple.cat.app.service.SettingsService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.simple.cat.app.common.GsonUtils.gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Dns
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by SergeyD on 28.06.2017.
 */
@Module
class NetModule {
    @Provides
    @Singleton
    fun provideOkhttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(100, TimeUnit.SECONDS)
        .connectTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(100, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)

    @Provides
    @Singleton
    fun provideCache(application: Application) = Cache(application.cacheDir, 10 * 1024 * 1024)

    @Provides
    @Singleton
    fun provideOkHttpClient(clientBuilder: OkHttpClient.Builder, cache: Cache): OkHttpClient =
        clientBuilder.cache(cache).build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(settingsService: SettingsService): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @Singleton
    fun provideRetrofitApiGenerator(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient.Builder
    ) = RetrofitApiGenerator(retrofitBuilder, okHttpClient)
}