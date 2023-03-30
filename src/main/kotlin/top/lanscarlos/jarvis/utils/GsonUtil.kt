package top.lanscarlos.jarvis.utils

import com.google.gson.Gson
import com.google.gson.JsonObject

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.utils
 *
 * @author Lanscarlos
 * @since 2023-03-30 14:24
 */

val gson = Gson()

fun String.toJson(): JsonObject {
    return gson.fromJson(this, JsonObject::class.java)
}