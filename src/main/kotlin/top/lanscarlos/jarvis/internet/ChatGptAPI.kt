package top.lanscarlos.jarvis.internet

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.internet
 *
 * @author Lanscarlos
 * @since 2023-03-30 13:34
 */
interface ChatGptAPI {
    @GET("/chatgpt")
    fun chat(@Field("q") question: String): Call<JsonObject>
}