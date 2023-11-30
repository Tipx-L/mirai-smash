package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
import io.github.tipx_l.miraismash.smash.Arena
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.buildMessageChain

class CreateArenaCommand :
	SimpleCommand(MiraiSmash, "开房", description = "开房") {
	@Handler
	suspend fun createArena(
		commandContext: CommandContext, arenaID: String, arenaPassword: String = "", arenaRemark: String = ""
	) {
		val sender = commandContext.sender
		val user = sender.user ?: return
		MiraiSmashData.arenaMap[user.id] = Arena(arenaID, arenaPassword, arenaRemark)
		sender.subject?.sendMessage(buildMessageChain {
			+commandContext.originalMessage.quote()
			+At(user)
			+" 已开房"
		})
	}
}
