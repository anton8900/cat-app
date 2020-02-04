package com.simple.cat.app.service.kitty

import com.simple.cat.app.model.Kitty
import com.simple.cat.app.repository.kitty.IKittyRepository

class KittyService(private val kittyRepository: IKittyRepository) {
    fun saveCat(kitty: Kitty) = kittyRepository.save(kitty)

    fun findAllCats() = kittyRepository.findAll()
}