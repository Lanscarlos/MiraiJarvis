package top.lanscarlos.jarvis.utils

import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.io.*
import java.util.concurrent.TimeUnit

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.utils
 *
 * @author Lanscarlos
 * @since 2022-12-19 17:09
 */

val httpClient by lazy {
    OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
}

fun HttpUrl.callUnsafe(): Response {
    val request = Request.Builder().url(this).build()
    return httpClient.newCall(request).execute()
}

fun HttpUrl.call(onResponse: (Response?) -> Unit) {
    val request = Request.Builder().url(this).build()
    httpClient.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onResponse(null)
        }
        override fun onResponse(call: Call, response: Response) {
            onResponse(response)
        }
    })
}

/**
 * 下载图片文件，文件名为其文件本体的 sha-1 签名
 * @return 图片访问是否成功
 * */
suspend fun Image.download(): Boolean {
    val input = this.queryUrl().toHttpUrl().callUnsafe().body?.byteStream() ?: return false
    val buffer = ByteArrayOutputStream()
    buffer.use { input.copyTo(it) }

    return ByteArrayInputStream(buffer.toByteArray()).use { buffered ->
        val target = File(dataFolder, buffered.digest("sha-1"))
        if (!target.create()) return@use false

        buffered.reset()

        target.outputStream().use {
            buffered.copyTo(it)
        }
        return@use true
    }
}

suspend fun Image.downloadTo(target: File): Boolean {
    target.create()
    val input = this.queryUrl().toHttpUrl().callUnsafe().body?.byteStream() ?: return false
    target.outputStream().use {
        input.copyTo(it)
    }
    return true
}