package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import kotlinx.coroutines.CancellationException
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
		MiraiSmash.arenaMap.remove(user.id)?.timeOut?.cancel(CancellationException("Manually shut down."))
		sender.subject?.sendMessage(buildMessageChain {
			+commandContext.originalMessage.quote()
			+At(user)
			+" 已关房"
		})
	}
}