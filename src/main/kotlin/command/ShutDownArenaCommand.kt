package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.buildMessageChain

class ShutDownArenaCommand : SimpleCommand(MiraiSmash, "关房", description = "关房") {
	@Handler
	suspend fun shutDownArena(commandContext: CommandContext) {
		val sender = commandContext.sender
		val user = sender.user ?: return
		MiraiSmashData.arenas.shutDownArena(sender)
		sender.subject?.sendMessage(buildMessageChain {
			+commandContext.originalMessage.quote()
			+At(user)
			+" 已关房"
		})
	}
}
