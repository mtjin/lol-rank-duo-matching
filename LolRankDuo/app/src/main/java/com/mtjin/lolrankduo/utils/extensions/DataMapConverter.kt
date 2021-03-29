package com.mtjin.lolrankduo.utils.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val gson = Gson()

fun <T> T.serializeToMap(): HashMap<String, Any> {
    return convert()
}

//convert a map to a data class
inline fun <reified T> HashMap<String, Any>.toDataClass(): T {
    return convert()
}

//convert an object of type I to type O
inline fun <I, reified O> I.convert(): O {
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}