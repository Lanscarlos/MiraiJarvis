package top.lanscarlos.jarvis.channel

import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import top.lanscarlos.jarvis.Context
import top.lanscarlos.jarvis.utils.info
import kotlin.coroutines.CoroutineContext

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis
 *
 * @author Lanscarlos
 * @since 2023-03-31 10:10
 */
object ConversationChannel : SimpleListenerHost() {

    private val pipeline = mutableListOf<ChannelHandler>()

    fun register(handler: ChannelHandler) {
        if (handler in pipeline) return
        pipeline.add(handler)
    }

    fun sortPipeline() {
        pipeline.sortByDescending { it.priority }
    }

    @EventHandler
    suspend fun e(e: FriendMessageEvent) {
        val message = e.message.contentToString()
        for (handler in pipeline) {
            info(handler.pattern.toString())
            if (!message.matches(handler.pattern)) continue
            handler.handle(e, message)
            break
        }
    }

    @EventHandler
    suspend fun e(e: GroupMessageEvent) {
        // 过滤未监听的群
        if (e.group.id !in Context.groupListened) return

        val message = e.message.contentToString()
        for (handler in pipeline) {
            if (!message.matches(handler.pattern)) continue
            handler.handle(e, message)
            break
        }
    }

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        exception.printStackTrace()
    }
}