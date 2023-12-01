package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
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
	override val usage: String
		get() = "/房间 [房间 ID] [房间密码] [房间备注]"

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
			val userID = user.id
			val arenas = MiraiSmashData.arenas
			arenas.removeIf {
				it.groupID == groupID && it.userID == userID
			}
			arenas += Arena(groupID, userID, arenaID, arenaPassword, arenaRemark)
			sender.subject?.sendMessage(buildMessageChain {
				+context.originalMessage.quote()
				+At(user)
				+" 已开房"
			})
		}

		suspend fun showAllArenas(context: CommandContext) {
			val sender = context.sender
			val user = sender.user ?: return
			val group = sender.getGroupOrNull() ?: return
			val arenas = ArrayList(MiraiSmashData.arenas).filter {
				it.groupID == group.id
			}
			val size = arenas.size
			sender.subject?.sendMessage(buildMessageChain {
				+context.originalMessage.quote()
				+At(user)

				if (size <= 0) {
					+if (Math.random() < 0.0625) " 没房，可以开一个捏亲" else " 当前没有房间，需要用 `/开房` 指令开一个吗？"
					return@buildMessageChain
				}

				+" 目前的房间有 "
				+size.toString()
				+" 个："
				arenas.forEach {
					val normalMember = group[it.userID] ?: return
					appendLine()
					appendLine("──────────")
					appendLine((Clock.System.now() - it.creationTime).toComponents { hours, minutes, _, _ ->
						buildString {
							var now = true

							val hoursNotZero = hours > 0

							if (hoursNotZero) {
								now = false
								append(hours)
								append(" 小时")
							}

							val minutesNotZero = minutes > 0

							if (hoursNotZero && minutesNotZero) append(' ')

							if (minutesNotZero) {
								if (now) now = false

								append(minutes)
								append(" 分钟")
							}

							append(if (now) "刚才" else "前")
						}
					})
					+"房主\t"
					val specialTitle = normalMember.specialTitle

					if (specialTitle.isNotEmpty()) {
						append('【')
						+specialTitle
						appendLine('】')
					} else appendLine(normalMember.nameCardOrNick)

					+"**ID**\t"
					+it.arenaID
					val arenaPassword = it.arenaPassword

					if (arenaPassword.isNotEmpty()) {
						appendLine()
						+"**密码**\t"
						+arenaPassword
					}

					val arenaRemark = it.arenaRemark

					if (arenaRemark.isNotEmpty()) {
						appendLine()
						+"备注\t"
						+it.arenaRemark
					}
				}
			})
		}
	}
}
