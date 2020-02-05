package com.simple.cat.app.repository

import android.content.Context
import com.simple.cat.app.common.GsonUtils.gson

import java.util.*

class PreferencesRepository(applicationContext: Context, name: String) {
    private val preferences = applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun getDate(key: String, defaultValue: Date?): Date? {
        val time = getLong(key, -1)
        return if(time == (-1).toLong()) defaultValue else Date(time)
    }

    fun saveDate(key: String, value: Date?) {
        saveLong(key, value?.time ?: -1)
    }

    fun saveBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun saveInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun saveLong(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }

    fun saveString(key: String, value: String?) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    fun getStringNotNull(key: String, defaultValue: String): String {
        return preferences.getString(key, defaultValue) ?: ""
    }

    fun saveUUID(key: String, value: UUID?) {
        if(value == null) {
            clearString(key)
        } else {
            saveString(key, value.toString())
        }
    }

    fun getUUID(key: String): UUID? {
        val stringId = preferences.getString(key, null)

        return if (stringId == null)
            null
        else {
            try {
                UUID.fromString(stringId)
            } catch (ex: Exception) {
                null
            }
        }
    }

    fun saveEnumValue(key: String, value: Any?) {
        if(value == null) {
            clearString(key)
        } else {
            saveString(key, value.toString())
        }
    }

    fun <T> getEnumValue(key: String, values: Array<T>, default: T?): T? {
        val stringData = getString(key, null)
        return if(stringData == null) {
            default
        } else {
            return values.find { it.toString() == stringData }
        }
    }

    fun saveObject(key: String, value: Any?) {
        if(value == null) {
            clearString(key)
        } else {
            saveString(key, gson.toJson(value))
        }
    }

    fun <T> getObject(key: String, type: Class<T>): T? {
        val json = getString(key, null)
        return if(json == null) {
            null
        } else {
            gson.fromJson(json, type)
        }
    }

    fun deleteAll() {
        preferences.edit().clear().apply()
    }

    fun clearString(key: String?) {
        preferences.edit().remove(key).apply()
    }
}

