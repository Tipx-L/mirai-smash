package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.smash.Arena
import kotlinx.datetime.Clock
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.contact.nameCardOrNick
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.buildMessageChain

class ArenaCommand : SimpleCommand(MiraiSmash, "房间", description = "房间") {
	@Handler
	suspend fun arena(
		context: CommandContext, arenaID: String = "", arenaPassword: String = "", arenaRemark: String = ""
	) {
		if (arenaID.isEmpty()) showAllArenas(context)
		else createArena(context, arenaID, arenaPassword, arenaRemark)
	}

	companion object {
		suspend fun createArena(
			context: CommandContext, arenaID: String, arenaPassword: String = "", arenaRemark: String = ""
		) {
			val sender = context.sender
			val groupID = sender.getGroupOrNull()?.id ?: return
			val user = sender.user ?: return
			val quote = context.originalMessage.quote()
			MiraiSmash.arenas += Arena(groupID, user.id, arenaID, arenaPassword, arenaRemark)
			sender.subject?.sendMessage(buildMessageChain {
				+quote
				+At(user)
				+" 已开房"
			})
		}

		suspend fun showAllArenas(context: CommandContext) {
			val sender = context.sender
			val user = sender.user ?: return
			val group = sender.getGroupOrNull() ?: return
			val arenas = MiraiSmash.arenas
			sender.subject?.sendMessage(buildMessageChain {
				+context.originalMessage.quote()
				+At(user)
				+" 目前的房间有"
				+arenas.size.toString()
				+"个"
				arenas.forEach {
					if (it.groupID != group.id) return

					val normalMember = group[it.userID] ?: return

					appendLine()
					+(Clock.System.now() - it.creationTime).toComponents { hours, minutes, seconds, _ ->
						buildString {
							var now = true

							if (hours > 0) {
								now = false
								append(hours)
								append("小时")
							}

							if (minutes > 0) {
								if (now) now = false

								append(minutes)
								append("分钟")
							}

							if (seconds > 0) {
								if (now) now = false

								append(seconds)
								append("秒")
							}

							append(if (now) "刚刚" else "前")
						}
					}
					+"\t房主："
					val specialTitle = normalMember.specialTitle

					if (specialTitle.isNotEmpty()) {
						append('【')
						+specialTitle
						append('】')
					}

					+normalMember.nameCardOrNick
					+"\tID："
					+it.arenaID
					val arenaPassword = it.arenaPassword

					if (arenaPassword.isNotEmpty()) {
						+"\t密码："
						+arenaPassword
					}

					val arenaRemark = it.arenaRemark

					if (arenaRemark.isNotEmpty()) {
						+"\t备注："
						+it.arenaRemark
					}
				}
			})
		}
	}
}
