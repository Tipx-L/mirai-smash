package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

object TestCommand : SimpleCommand(MiraiSmash, "测试", description = "测试") {
	@Handler
	suspend fun test(context: CommandContext) {
		val bufferedImage = BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB)
		val graphics = bufferedImage.createGraphics()
		graphics.drawString(MiraiSmashData.arenas.joinToString("\n"), 0F, 0F)
		val byteArrayOutputStream = ByteArrayOutputStream()
		withContext(Dispatchers.IO) {
			ImageIO.write(bufferedImage, "png", byteArrayOutputStream)
		}
		val imageStream = ByteArrayInputStream(byteArrayOutputStream.toByteArray())
		context.sender.subject?.sendImage(imageStream)
		withContext(Dispatchers.IO) {
			imageStream.close()
		}
	}
}