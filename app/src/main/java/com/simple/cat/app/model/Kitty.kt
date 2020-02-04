package com.simple.cat.app.model

import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel
import com.simple.cat.app.db.AppDatabase

@Table(database = AppDatabase::class)
class Kitty: BaseModel() {
    @PrimaryKey
    var id: String? = null

    @Column
    var url: String? = null

    @Column
    var width: Int = 0

    @Column
    var height: Int = 0

    fun resize(screenWidth: Int) {
        if(screenWidth != 0 && width != 0) {
            height = (height * screenWidth.toDouble() / width).toInt()
            width = screenWidth
        }
    }
}