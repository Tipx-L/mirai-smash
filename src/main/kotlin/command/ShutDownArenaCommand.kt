package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.buildMessageChain

class ShutDownArenaCommand : SimpleCommand(MiraiSmash, "关房", description = "关房") {
	override val usage: String
		get() = "/关房"

	@Handler
	suspend fun shutDownArena(context: CommandContext) {
		val sender = context.sender
		val group = sender.getGroupOrNull() ?: return
		val user = sender.user ?: return
		sender.subject?.sendMessage(buildMessageChain {
			+context.originalMessage.quote()
			+At(user)
			+if (MiraiSmashData.arenas.removeIf {
					it.groupID == group.id && it.userID == user.id
				}) " 已关房" else " 并未查询到可关闭的房间"
		})
	}
}
