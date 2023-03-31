package top.lanscarlos.jarvis.conversation

import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import top.lanscarlos.jarvis.Context
import top.lanscarlos.jarvis.channel.ChannelHandler

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.conversation
 *
 * @author Lanscarlos
 * @since 2023-03-31 10:59
 */
object ReloadCommandConversation : ChannelHandler {

    override val priority: Int = ChannelHandler.PRIORITY_HIGHEST

    override val pattern: Regex = "\\$ reload config".toRegex()

    override suspend fun handle(event: FriendMessageEvent, rawMessage: String) {
        if (!Context.isOwner(event.friend.id)) return

        Context.config.reload()
        event.friend.sendMessage("配置文件重载完毕...")
    }

    override suspend fun handle(event: GroupMessageEvent, rawMessage: String) {
    }
}