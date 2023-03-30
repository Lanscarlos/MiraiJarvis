package top.lanscarlos.jarvis.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.utils
 *
 * @author Lanscarlos
 * @since 2023-03-30 13:27
 */

val retrofitFaithl by lazy {
    Retrofit.Builder()
        .baseUrl("https://api.faithl.com/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}