package top.lanscarlos.jarvis

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * MiraiJarvis
 * top.lanscarlos.jarvis
 *
 * @author Lanscarlos
 * @since 2022-12-19 21:31
 */
object Context {

//    val config by lazy {
//        Configuration.loadFromFile(releaseResourceFile("config.yml", false))
//    }

    val scope by lazy {
        CoroutineScope(Dispatchers.Default)
    }
}