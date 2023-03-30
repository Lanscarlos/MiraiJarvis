package top.lanscarlos.jarvis.conversation

import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import okhttp3.OkHttpClient
import okhttp3.Request
import top.lanscarlos.jarvis.utils.toJson
import java.net.URLEncoder
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.conversation
 *
 * @author Lanscarlos
 * @since 2023-03-30 13:30
 */
object ChatGptConversation : SimpleListenerHost() {

    @EventHandler
    suspend fun e(e: FriendMessageEvent) {
        process(e.message)?.let {
            e.friend.sendMessage(it)
        }
    }

    @EventHandler
    suspend fun e(e: GroupMessageEvent) {
        if (e.group.id != 250127377L) return

        process(e.message)?.let {
            e.group.sendMessage(it)
        }
    }

    val httpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    const val base = "https://api.faithl.com/chatgpt"

    fun process(message: MessageChain): MessageChain? {
        val msg = message.contentToString()

        // 过滤非对话信息
        if (msg[0] != '#') return null
        val request = Request.Builder()
            .url("$base?q=${URLEncoder.encode(msg.substring(1).trim(), "UTF-8")}")
            .build()
        val response = httpClient.newCall(request).execute().body?.string()?.toJson()

        return if (response != null) {
            message.quote() + response["data"].asJsonObject["answer"].asString
        } else {
            message.quote() + "404 我被外星人劫持咯~"
        }
    }

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        exception.printStackTrace()
    }
}