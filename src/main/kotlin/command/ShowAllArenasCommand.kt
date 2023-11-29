package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
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
		sender.subject?.sendMessage(buildMessageChain {
			+commandContext.originalMessage.quote()
			+At(user)
			+" 目前的房间有${MiraiSmash.arenaMap.size}个"
			+MiraiSmash.arenaMap.values.joinToString { "\n${it.arenaID} 密码${it.arenaPassword} 备注“${it.arenaRemark}”" }
		})
	}
}