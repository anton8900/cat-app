package com.simple.cat.app.service.kitty

import com.simple.cat.app.model.Kitty
import com.simple.cat.app.repository.kitty.IKittyRepository
import com.simple.cat.app.server.api.KittyApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class KittyService(private val kittyRepository: IKittyRepository, private val kittyApi: KittyApi) {
    private val loadKittiesThread = Executors.newFixedThreadPool(1)
    private val pageSize = 10

    fun saveCat(kitty: Kitty) = kittyRepository.save(kitty)

    fun findAllCats() = kittyRepository.findAll()

    fun loadKitties(page: Int): Observable<List<Kitty>> {
        return kittyApi.getKitties(pageSize, page).subscribeOn(Schedulers.from(loadKittiesThread))
    }
}