package com.simple.cat.app.service

import android.content.Context
import com.raizlabs.android.dbflow.config.FlowManager
import com.simple.cat.app.common.FileUtils
import com.simple.cat.app.repository.PreferencesRepository
import java.io.File
import java.io.IOException
import java.util.*

class SettingsService(context: Context){
    private val contextRepository = PreferencesRepository(context, "context")

    var language: String
        get() = contextRepository.getStringNotNull("language", "en")
        set(language) = contextRepository.saveString("language", language)
}