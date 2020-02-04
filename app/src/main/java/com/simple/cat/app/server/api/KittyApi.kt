package com.simple.cat.app.server.api

import com.simple.cat.app.model.Kitty
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface KittyApi {
    /**
     * Synchronize Device
     *
     * @param eventId  (required)
     * @param loadLogs  (required)
     * @param forceInitial  (optional)
     * @return Call&lt;DeviceSynchronizationOutputDto&gt;
     */
    @GET("v1/images/search")
    fun getKitties(@Query("limit") limit: Int? = 100, @Query("page") page: Int?): Observable<List<Kitty>>
}