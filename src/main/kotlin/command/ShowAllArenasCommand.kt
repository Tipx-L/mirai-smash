package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.buildMessageChain

class ShowAllArenasCommand : SimpleCommand(MiraiSmash, "查房", "房间", description = "查房") {
	@Handler
	suspend fun showAllArenas(commandContext: CommandContext) {
		val sender = commandContext.sender
		val user = sender.user ?: return
		val arenas = MiraiSmashData.arenas
		sender.subject?.sendMessage(buildMessageChain {
			+commandContext.originalMessage.quote()
			+At(user)
			+" 目前的房间有"
			arenas.size
			+"个："
			+arenas.joinToString {
				buildString {
					appendLine()
					append(it.creationTime.toLocalDateTime(TimeZone.currentSystemDefault()))
					append('\t')
					append("\tID：")
					append(it.arenaID)
					append("\t密码：")
					append(it.arenaPassword)
					append("\t备注：")
					append(it.arenaRemark)
				}
			}
		})
	}
}
