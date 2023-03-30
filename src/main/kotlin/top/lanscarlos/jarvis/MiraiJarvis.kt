package top.lanscarlos.jarvis

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.registerTo
import top.lanscarlos.jarvis.conversation.ChatGptConversation
import top.lanscarlos.jarvis.conversation.MetricsConversation
import top.lanscarlos.jarvis.utils.info

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis
 *
 * @author Lanscarlos
 * @since 2022-12-19 17:06
 */
object MiraiJarvis : KotlinPlugin(
    JvmPluginDescription(
        id = "top.lanscarlos.jarvis",
        name = "MiraiJarvis",
        version = "1.0.0"
    ) {
        author("Lanscarlos")
    }
) {

    const val pluginName = "MiraiJarvis"

    override fun onEnable() {
        ChatGptConversation.registerTo(GlobalEventChannel)
        MetricsConversation.registerTo(GlobalEventChannel)

//        ExampleConversation.registerTo(GlobalEventChannel)

        info("Plugin loaded. XD")
    }
}