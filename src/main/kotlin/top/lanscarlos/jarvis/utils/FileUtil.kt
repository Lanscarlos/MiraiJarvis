package top.lanscarlos.jarvis.utils

import taboolib.common.io.newFile
import java.io.File
import top.lanscarlos.jarvis.MiraiJarvis

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.utils
 *
 * @author Lanscarlos
 * @since 2022-12-19 17:09
 */

val dataFolder by lazy {
    File("./plugins/${MiraiJarvis.pluginName}/")
}

val configFile by lazy {
    File(dataFolder, "config.yml")
}

/**
 * 创建文件/文件夹
 * */
fun File.create(): Boolean {
    if (!parentFile.exists()) parentFile.mkdirs()
    if (this.exists()) return false
    return if (isDirectory) this.mkdirs() else createNewFile()
}

/**
 * @from TabooLib
 * */
fun releaseResourceFile(path: String, replace: Boolean): File {
    val file = File(dataFolder, path)
    if (file.exists() && !replace) {
        return file
    }
    newFile(file).writeBytes(MiraiJarvis.getResourceAsStream(path)?.readBytes() ?: error("resource not found: $path"))
    return file
}

