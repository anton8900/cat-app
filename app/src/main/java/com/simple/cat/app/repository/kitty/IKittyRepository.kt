package com.simple.cat.app.repository.kitty

import com.simple.cat.app.model.Kitty
import io.reactivex.Observable

interface IKittyRepository {
    fun save(kitty: Kitty): Observable<Boolean>
    fun findAll(): Observable<List<Kitty>>
}