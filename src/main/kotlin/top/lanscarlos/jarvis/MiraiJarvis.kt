package top.lanscarlos.jarvis

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.registerTo
import taboolib.module.configuration.Configuration
import top.lanscarlos.jarvis.conversation.ExampleConversation
import top.lanscarlos.jarvis.utils.info
import top.lanscarlos.jarvis.utils.releaseResourceFile

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

    lateinit var config: Configuration
        private set

    lateinit var jarvis: Bot
        private set

    override fun onEnable() {

        config = Configuration.loadFromFile(releaseResourceFile("config.yml", false))

        val account = config.getLong("account-setting.user", -1)
        val password = config.getString("account-setting.password") ?: "Undefined"

        if (account > 0) {
            jarvis = BotFactory.newBot(account, password)
            CoroutineScope(Dispatchers.Default).launch {
                jarvis.login()
            }
        }

        info("building jarvis(account=$account)")
        info("Plugin loaded. XD")

        ExampleConversation.registerTo(GlobalEventChannel)
    }
}