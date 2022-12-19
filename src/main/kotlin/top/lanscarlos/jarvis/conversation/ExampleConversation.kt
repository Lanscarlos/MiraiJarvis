package top.lanscarlos.jarvis.conversation

import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.findIsInstance
import top.lanscarlos.jarvis.utils.download

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.conversation
 *
 * @author Lanscarlos
 * @since 2022-12-19 17:12
 */
object ExampleConversation : SimpleListenerHost() {

    @EventHandler
    suspend fun e(e: FriendMessageEvent) {
        val image = e.message.findIsInstance<Image>() ?: let {
            e.friend.sendMessage(e.message.quote() + "Image Not Found...")
            return
        }

        if (image.download()) {
            e.friend.sendMessage(e.message.quote() + "Image Download Succeeded...")
        } else {
            e.friend.sendMessage(e.message.quote() + "Image Exist!")
        }
    }

}