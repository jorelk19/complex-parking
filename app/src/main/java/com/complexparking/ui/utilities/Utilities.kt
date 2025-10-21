package com.complexparking.ui.utilities

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T : Any> String.fromJson(): T = Gson().fromJson<T>(this, object : TypeToken<T>() {}.type)
inline fun <reified T : Any> T.json(): String = Gson().toJson(this, T::class.java)
