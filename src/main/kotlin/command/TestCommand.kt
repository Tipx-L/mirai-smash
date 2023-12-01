package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

object TestCommand : SimpleCommand(MiraiSmash, "测试", description = "测试") {
	@Handler
	suspend fun test(context: CommandContext) {
		val arenasInformation = MiraiSmashData.arenas.flatMap {
			val strings = ArrayList<String>()
			strings += (Clock.System.now() - it.creationTime).toComponents { hours, minutes, _, _ ->
				buildString {
					var now = true

					val hoursNotZero = hours > 0

					if (hoursNotZero) {
						now = false
						append(hours)
						append(" 小时")
					}

					val minutesNotZero = minutes > 0

					if (hoursNotZero && minutesNotZero) append(' ')

					if (minutesNotZero) {
						if (now) now = false

						append(minutes)
						append(" 分钟")
					}

					append(if (now) "刚才" else "前")
				}
			}
			strings += buildString {
				append("房主\t")
				append(it.userID.toString())
			}
			strings += buildString {
				append("**ID**\t")
				append(it.arenaID)
			}
			val arenaPassword = it.arenaPassword

			if (arenaPassword.isNotEmpty()) strings += buildString {
				append("**密码**\t")
				append(arenaPassword)
			}

			val arenaRemark = it.arenaRemark

			if (arenaRemark.isNotEmpty()) strings += buildString {
				append("备注\t")
				append(arenaRemark)
			}

			strings
		}
		val bufferedImage = BufferedImage(3840, 80 * arenasInformation.size, BufferedImage.TYPE_INT_RGB)
		val graphics = bufferedImage.createGraphics()
		graphics.font = Font("SansSerif", Font.PLAIN, 80)
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP)
		arenasInformation.forEachIndexed { index, s ->
			graphics.drawString(s, 0, 80 * (index + 1))
		}
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