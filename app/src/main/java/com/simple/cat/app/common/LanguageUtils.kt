package com.simple.cat.app.common

import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

object LanguageUtils {
    fun changeLanguage(locale: Locale, res: Resources) {
        Locale.setDefault(locale)
        val config = Configuration(res.configuration)
        config.locale = locale
        res.updateConfiguration(config, res.displayMetrics)
    }
}