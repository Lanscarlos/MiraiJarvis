package top.lanscarlos.jarvis.channel

import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis
 *
 * @author Lanscarlos
 * @since 2023-03-31 10:11
 */
interface ChannelHandler {

    // 优先级，值越大优先级越高
    val priority: Int

    val pattern: Regex

    suspend fun handle(event: FriendMessageEvent, rawMessage: String)

    suspend fun handle(event: GroupMessageEvent, rawMessage: String)

    companion object {
        const val PRIORITY_HIGHEST = 32
        const val PRIORITY_HIGH = 16
        const val PRIORITY_NORMAL = 8
        const val PRIORITY_LOW = 4
        const val PRIORITY_LOWEST = 0
    }
}