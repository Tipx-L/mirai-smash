package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.buildMessageChain
import java.time.LocalDateTime
import java.time.ZoneId

class ShowAllArenasCommand : SimpleCommand(MiraiSmash, "查房", "房间", description = "查房") {
	@Handler
	suspend fun showAllArenas(commandContext: CommandContext) {
		val sender = commandContext.sender
		val user = sender.user ?: return
		val arenaMap = MiraiSmashData.arenaMap
		sender.subject?.sendMessage(buildMessageChain {
			+commandContext.originalMessage.quote()
			+At(user)
			+" 目前的房间有"
			arenaMap.size
			+"个"
			+arenaMap.values.joinToString {
				buildString {
					appendLine()
					append(
						LocalDateTime.ofInstant(
							it.creationTime, ZoneId.systemDefault()
						)
					)
					append(' ')
					append(it.arenaID)
					append(" 密码")
					append(it.arenaPassword)
					append(" 备注“")
					append(it.arenaRemark)
					append('”')
				}
			}
		})
	}
}