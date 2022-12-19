package top.lanscarlos.jarvis.utils

import top.lanscarlos.jarvis.MiraiJarvis

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.utils
 *
 * @author Lanscarlos
 * @since 2022-12-19 17:07
 */

fun info(message: String) {
    MiraiJarvis.logger.info("[${MiraiJarvis.pluginName} | INFO] $message")
}

fun warn(message: String) {
    MiraiJarvis.logger.info("[${MiraiJarvis.pluginName} | WARN] $message")
}