package top.lanscarlos.jarvis.conversation

import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.TranscodingHints
import org.apache.batik.transcoder.image.ImageTranscoder
import org.apache.batik.transcoder.image.PNGTranscoder
import top.lanscarlos.jarvis.utils.*
import java.io.*
import java.util.*
import kotlin.coroutines.CoroutineContext


/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.conversation
 *
 * @author Lanscarlos
 * @since 2023-03-30 17:57
 */
object MetricsConversation : SimpleListenerHost() {

    @EventHandler
    suspend fun e(e: FriendMessageEvent) {
        process(e.friend, e.message)
    }

    @EventHandler
    suspend fun e(e: GroupMessageEvent) {
        if (e.group.id != 250127377L) return

        process(e.group, e.message)
    }

    const val prefix = "https://bstats.org/signatures/bukkit/"
    const val suffix = ".svg"

    suspend fun process(contact: Contact, message: MessageChain) {
        val msg = message.contentToString()
        if (!msg.matches("/(metrics|b?stats) \\w+".toRegex())) return

        val plugin = msg.substringAfter(' ')
        val url = (prefix + plugin + suffix).toHttpUrl()
        url.callUnsafe().body?.byteStream()?.use { input ->
            svgToPng(input).use { output ->
                val image = contact.uploadImage(imageStream = output, formatName = "png")
                contact.sendMessage(message.quote() + image)
            }
        }
    }

    fun svgToPng(input: InputStream): InputStream {
        val output = ByteArrayOutputStream()
        val transcoder = PNGTranscoder()
        val transcoderInput = TranscoderInput(input)
        val transcoderOutput = TranscoderOutput(output)
        transcoder.transcode(transcoderInput, transcoderOutput)
        return ByteArrayInputStream(output.use { it.toByteArray() })
    }

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        exception.printStackTrace()
    }
}