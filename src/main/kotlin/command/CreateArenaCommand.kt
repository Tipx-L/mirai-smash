package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
import io.github.tipx_l.miraismash.smash.Arena
import io.github.tipx_l.miraismash.smash.ArenaList
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.buildMessageChain

class CreateArenaCommand : SimpleCommand(MiraiSmash, "开房", "房间", description = "开房") {
	@Handler
	suspend fun createArena(
		context: CommandContext, arenaID: String, arenaPassword: String = "", arenaRemark: String = ""
	) {
		val sender = context.sender
		val user = sender.user ?: return
		MiraiSmashData.arenas as ArenaList += Arena(context, arenaID, arenaPassword, arenaRemark)
		sender.subject?.sendMessage(buildMessageChain {
			+context.originalMessage.quote()
			+At(user)
			+" 已开房"
		})
	}
}
