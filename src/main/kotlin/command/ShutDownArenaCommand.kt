package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.buildMessageChain

class ShutDownArenaCommand : SimpleCommand(MiraiSmash, "关房", description = "关房") {
	override val usage: String
		get() = "/开房 <房间 ID> [房间密码] [房间备注]"

	@Handler
	suspend fun shutDownArena(context: CommandContext) {
		val sender = context.sender
		val group = sender.getGroupOrNull() ?: return
		val user = sender.user ?: return
		MiraiSmash.arenas.removeIf {
			it.groupID == group.id && it.userID == user.id
		}
		sender.subject?.sendMessage(buildMessageChain {
			+context.originalMessage.quote()
			+At(user)
			+" 已关房"
		})
	}
}
