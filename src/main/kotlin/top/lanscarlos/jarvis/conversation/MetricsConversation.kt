package top.lanscarlos.jarvis.conversation

import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import top.lanscarlos.jarvis.channel.ChannelHandler
import top.lanscarlos.jarvis.utils.*
import java.io.*


/**
 * MiraiJarvis
 * top.lanscarlos.jarvis.conversation
 *
 * @author Lanscarlos
 * @since 2023-03-30 17:57
 */
object MetricsConversation : ChannelHandler {

    override val priority: Int = ChannelHandler.PRIORITY_NORMAL

    override val pattern: Regex = "# (metrics|b?stats) \\w+".toRegex()

    const val prefix = "https://bstats.org/signatures/bukkit/"
    const val suffix = ".svg"

    suspend fun process(contact: Contact, message: MessageChain, rawMessage: String) {
        val plugin = rawMessage.substringAfterLast(' ')
        val url = (prefix + plugin + suffix).toHttpUrl()
        url.callUnsafe().body?.byteStream()?.use { input ->
            svgToPng(input).use { output ->
                val image = contact.uploadImage(imageStream = output, formatName = "png")
                contact.sendMessage(message.quote() + image)
            }
        } ?: contact.sendMessage(message.quote() + "404 Not Found: $plugin")
    }

    fun svgToPng(input: InputStream): InputStream {
        val output = ByteArrayOutputStream()
        val transcoder = PNGTranscoder()
        val transcoderInput = TranscoderInput(input)
        val transcoderOutput = TranscoderOutput(output)
        transcoder.transcode(transcoderInput, transcoderOutput)
        return ByteArrayInputStream(output.use { it.toByteArray() })
    }

    override suspend fun handle(event: FriendMessageEvent, rawMessage: String) {
        process(event.friend, event.message, rawMessage)
    }

    override suspend fun handle(event: GroupMessageEvent, rawMessage: String) {
        process(event.group, event.message, rawMessage)
    }
}