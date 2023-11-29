package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.smash.Arena
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.buildMessageChain
import kotlin.time.Duration.Companion.hours

class CreateArenaCommand : SimpleCommand(MiraiSmash, "开房", description = "开房") {
	@Handler
	suspend fun createArena(
		commandContext: CommandContext, arenaID: String, arenaPassword: String, arenaRemark: String
	) {
		val sender = commandContext.sender
		val user = sender.user ?: return
		val arenaMap = MiraiSmash.arenaMap
		val id = user.id
		val at = At(user)
		arenaMap.remove(id)?.timeOut?.cancel(CancellationException("Replaced by a newly created arena."))
		arenaMap[id] = Arena(arenaID, arenaPassword, arenaRemark, MiraiSmash.launch {
			delay(8.hours)
			arenaMap.remove(id)
			sender.subject?.sendMessage(buildMessageChain {
				+at
				+" 房间超过8小时未关 超时已关闭"
			})
		})
		sender.subject?.sendMessage(buildMessageChain {
			+commandContext.originalMessage.quote()
			+at
			+" 已开房"
		})
	}
}
