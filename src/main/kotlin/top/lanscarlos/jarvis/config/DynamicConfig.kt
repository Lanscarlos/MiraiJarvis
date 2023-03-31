package top.lanscarlos.jarvis.config

import taboolib.common5.*
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration
import java.io.File
import java.util.concurrent.CopyOnWriteArraySet

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.config
 *
 * @author Lanscarlos
 * @since 2023-03-31 11:33
 */
class DynamicConfig(
    val source: Configuration
) {

    val nodes = CopyOnWriteArraySet<DynamicConfigNode<*>>()

    fun reload() {
        source.reload()
        nodes.forEach { it.updateValue() }
    }

    fun write(path: String, value: Any?, save: Boolean = false, reload: Boolean = false) {
        source[path] = value
        if (save) save(reload = reload)
    }

    fun save(file: File? = null, reload: Boolean = false) {
        source.saveToFile(file ?: source.file)
        if (reload) reload()
    }

    fun saveTo(file: File) {
        source.saveToFile(file)
    }

    fun <T> read(path: String, transfer: ConfigurationSection.(Any?) -> T): DynamicConfigNode<T> {
        return DynamicConfigNode(this, path, transfer).also {
            nodes += it
        }
    }

    fun readBoolean(path: String, def: Boolean = false): DynamicConfigNode<Boolean> {
        return DynamicConfigNode(this, path) {
            it?.cbool ?: def
        }.also {
            nodes += it
        }
    }

    fun readShort(path: String, def: Short = 0): DynamicConfigNode<Short> {
        return DynamicConfigNode(this, path) {
            it?.cshort ?: def
        }.also {
            nodes += it
        }
    }

    fun readInt(path: String, def: Int = 0): DynamicConfigNode<Int> {
        return DynamicConfigNode(this, path) {
            it?.cint ?: def
        }.also {
            nodes += it
        }
    }

    fun readLong(path: String, def: Long = 0): DynamicConfigNode<Long> {
        return DynamicConfigNode(this, path) {
            it?.clong ?: def
        }.also {
            nodes += it
        }
    }

    fun readFloat(path: String, def: Float = 0f): DynamicConfigNode<Float> {
        return DynamicConfigNode(this, path) {
            it?.cfloat ?: def
        }.also {
            nodes += it
        }
    }

    fun readDouble(path: String, def: Double = 0.0): DynamicConfigNode<Double> {
        return DynamicConfigNode(this, path) {
            it?.cdouble ?: def
        }.also {
            nodes += it
        }
    }

    fun readString(path: String): DynamicConfigNode<String?> {
        return DynamicConfigNode(this, path) {
            it?.toString()
        }.also {
            nodes += it
        }
    }

    fun readString(path: String, def: String): DynamicConfigNode<String> {
        return DynamicConfigNode(this, path) {
            it?.toString() ?: def
        }.also {
            nodes += it
        }
    }

    fun readIntList(path: String, def: List<Int> = emptyList()): DynamicConfigNode<List<Int>> {
        return DynamicConfigNode(this, path) { value ->
            when (value) {
                is Int -> listOf(value)
                is String -> listOf(value.cint)
                is Array<*> -> value.mapNotNull { it?.cint }
                is Collection<*> -> value.mapNotNull { it?.cint }
                else -> def
            }
        }.also {
            nodes += it
        }
    }

    fun readStringList(path: String, def: List<String> = emptyList()): DynamicConfigNode<List<String>> {
        return DynamicConfigNode(this, path) { value ->
            when (value) {
                is String -> listOf(value)
                is Array<*> -> value.mapNotNull { it?.toString() }
                is Collection<*> -> value.mapNotNull { it?.toString() }
                else -> def
            }
        }.also {
            nodes += it
        }
    }

    companion object {
        fun Configuration.toDynamic(): DynamicConfig = DynamicConfig(this)
    }
}