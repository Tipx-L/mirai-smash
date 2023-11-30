package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.buildMessageChain

class ShutDownArenaCommand : SimpleCommand(MiraiSmash, "关房", description = "关房") {
	@Handler
	suspend fun shutDownArena(context: CommandContext) {
		val sender = context.sender
		val user = sender.user ?: return
		MiraiSmash.arenas.shutDownArena(sender)
		sender.subject?.sendMessage(buildMessageChain {
			+context.originalMessage.quote()
			+At(user)
			+" 已关房"
		})
	}
}
