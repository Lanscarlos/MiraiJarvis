package top.lanscarlos.jarvis.config

import taboolib.library.configuration.ConfigurationSection
import kotlin.reflect.KProperty

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.config
 *
 * @author Lanscarlos
 * @since 2023-03-31 11:35
 */
class DynamicConfigNode<T>(
    val parent: DynamicConfig,
    val path: String,
    val transfer: ConfigurationSection.(Any?) -> T
) {

    var isInitialized = false
    var raw: Any? = null
    var value: T? = null

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(any: Any?, property: KProperty<*>): T {
        if (!isInitialized) {
            raw = parent.source[path]
            value = transfer(parent.source, raw)
            isInitialized = true
        }
        return value as T
    }

    operator fun setValue(context: Any?, property: KProperty<*>, value: T) {
        if (this.value == value) return
        this.value = value
        parent.write(path, value, save = true)
    }

    fun updateValue() {
        val newRaw = parent.source[path]
        // 判断前后数值发生变化
        if (raw == newRaw) return

        // 更新数据
        this.raw = newRaw
        this.value = transfer(parent.source, newRaw)
    }
}