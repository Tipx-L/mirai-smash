package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
import io.github.tipx_l.miraismash.smash.Arena
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.buildMessageChain

object CreateArenaCommand : SimpleCommand(MiraiSmash, "开房", description = "开房") {
	override val usage: String
		get() = "/开房 <房间 ID> [房间密码] [房间备注]"

	@Handler
	suspend fun createArena(
		context: CommandContext, arenaID: String, arenaPassword: String = "", arenaRemark: String = ""
	) {
		val sender = context.sender
		val groupID = sender.getGroupOrNull()?.id ?: return
		val user = sender.user ?: return

		if (!arenaID.matches(MiraiSmash.arenaIDFormat)) {
			sender.subject?.sendMessage(buildMessageChain {
				+context.originalMessage.quote()
				+At(user)
				+" 房间 ID 不是合法格式！"
			})
			return
		}

		if (!arenaPassword.matches(MiraiSmash.arenaPasswordFormat)) {
			sender.subject?.sendMessage(buildMessageChain {
				+context.originalMessage.quote()
				+At(user)
				+" 房间密码不是合法格式！"
			})
			return
		}

		val userID = user.id
		val arenas = MiraiSmashData.arenas
		arenas.removeIf {
			it.groupID == groupID && it.userID == userID
		}
		arenas += Arena(groupID, userID, arenaID.uppercase(), arenaPassword, arenaRemark)
		sender.subject?.sendMessage(buildMessageChain {
			+context.originalMessage.quote()
			+At(user)
			+" 已开房"
		})
	}
}
