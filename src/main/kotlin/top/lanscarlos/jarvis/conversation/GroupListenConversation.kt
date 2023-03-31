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
 * @since 2023-03-31 10:43
 */
object GroupListenConversation : ChannelHandler {

    override val priority: Int = ChannelHandler.PRIORITY_HIGH

    override val pattern: Regex = "\\$ (group-listen|gl) (info|add|remove|rm)( \\d+)?".toRegex()

    override suspend fun handle(event: FriendMessageEvent, rawMessage: String) {
        if (!Context.isOwner(event.friend.id)) return

        val matcher = pattern.matchEntire(rawMessage) ?: return

        val operate = matcher.groupValues[2]
        val groupId = matcher.groups[3]?.value?.substring(1)?.toLong()
        when (operate) {
            "info" -> {
                val message = if (groupId == null) {
                    "正在监听的群聊：\n" + Context.groupListened.joinToString(separator = "\n") { it.toString() }
                } else {
                    "群 $groupId 的监听状态为 ${Context.groupListened.contains(groupId)}"
                }
                event.friend.sendMessage(message)
            }
            "add" -> {
                if (groupId == null) {
                    event.friend.sendMessage("请输入群号")
                    return
                }
                Context.addGroupListened(groupId)
                event.friend.sendMessage("已添加群 $groupId 的监听")
            }
            "remove", "rm" -> {
                if (groupId == null) {
                    event.friend.sendMessage("请输入群号")
                    return
                }
                Context.removeGroupListened(groupId)
                event.friend.sendMessage("已移除群 $groupId 的监听")
            }
        }
    }

    override suspend fun handle(event: GroupMessageEvent, rawMessage: String) {
    }
}