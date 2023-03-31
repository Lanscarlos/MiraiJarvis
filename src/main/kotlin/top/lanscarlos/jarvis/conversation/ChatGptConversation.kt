package top.lanscarlos.jarvis.conversation

import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import okhttp3.OkHttpClient
import okhttp3.Request
import top.lanscarlos.jarvis.channel.ChannelHandler
import top.lanscarlos.jarvis.utils.toJson
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.conversation
 *
 * @author Lanscarlos
 * @since 2023-03-30 13:30
 */
object ChatGptConversation : ChannelHandler {

    override val priority: Int = ChannelHandler.PRIORITY_LOWEST

    override val pattern: Regex = "# .*".toRegex()

    val httpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    const val base = "https://api.faithl.com/chatgpt"

    fun process(rawMessage: String, message: MessageChain): MessageChain {
        val request = Request.Builder()
            .url("$base?q=${URLEncoder.encode(rawMessage.substring(1).trim(), "UTF-8")}")
            .build()
        val response = httpClient.newCall(request).execute().body?.string()?.toJson()

        return if (response != null) {
            message.quote() + response["data"].asJsonObject["answer"].asString
        } else {
            message.quote() + "404 我被外星人劫持咯~"
        }
    }

    override suspend fun handle(event: FriendMessageEvent, rawMessage: String) {
        event.friend.sendMessage(process(rawMessage, event.message))
    }

    override suspend fun handle(event: GroupMessageEvent, rawMessage: String) {
        event.group.sendMessage(process(rawMessage, event.message))
    }
}