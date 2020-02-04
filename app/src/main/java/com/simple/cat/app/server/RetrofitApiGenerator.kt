package com.simple.cat.app.server

import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RetrofitApiGenerator(private val builder: Retrofit.Builder, private val httpClientBuilder: OkHttpClient.Builder) {
    fun <S> createService(serviceClass: Class<S>): S {
        return builder.client(httpClientBuilder.build()).build().create(serviceClass)
    }
}
