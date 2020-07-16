package com.library.core.model.local.preference

import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun SharedPreferences.Int(
    key: (KProperty<*>) -> String = KProperty<*>::name,
    defaultValue: Int = 0
): ReadWriteProperty<Any, Int> = object : ReadWriteProperty<Any, Int> {
    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return getInt(key(property), defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        edit { putInt(key(property), value) }
    }
}

fun SharedPreferences.String(
    key: String ,
    defaultValue: String = ""
): ReadWriteProperty<Any, String> = object : ReadWriteProperty<Any, String> {
    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return getString(key, defaultValue).toString()
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        edit { putString(key, value) }
    }
}
