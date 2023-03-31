package top.lanscarlos.jarvis

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import taboolib.common5.clong
import taboolib.module.configuration.Configuration
import top.lanscarlos.jarvis.config.DynamicConfig.Companion.toDynamic
import top.lanscarlos.jarvis.utils.configFile
import top.lanscarlos.jarvis.utils.releaseResourceFile

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis
 *
 * @author Lanscarlos
 * @since 2022-12-19 21:31
 */
object Context {

    val config by lazy {
        if (!configFile.exists()) {
            releaseResourceFile("config.yml", false)
        }
        Configuration.loadFromFile(configFile).toDynamic()
    }

    val scope by lazy {
        CoroutineScope(Dispatchers.Default)
    }

    val owner by config.readLong("owner-setting.account", -1)

    var groupListened by config.read("group-setting.listen") { value ->
        when (value) {
            is String -> setOf(value.clong)
            is Array<*> -> value.mapNotNull { it?.clong }.toSet()
            is Collection<*> -> value.mapNotNull { it?.clong }.toSet()
            else -> setOf()
        }
    }

    fun isOwner(contactId: Long): Boolean {
        return contactId == owner
    }

    fun addGroupListened(groupId: Long) {
        if (groupId in groupListened) return
        groupListened = groupListened + groupId
    }

    fun removeGroupListened(groupId: Long) {
        if (groupId !in groupListened) return
        groupListened = groupListened - groupId
    }
}