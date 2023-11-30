package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand

class CreateArenaCommand : SimpleCommand(MiraiSmash, "开房", description = "开房") {
	override val usage: String
		get() = "/开房 <房间 ID> [房间密码] [房间备注]"

	@Handler
	suspend fun createArena(
		context: CommandContext, arenaID: String, arenaPassword: String = "", arenaRemark: String = ""
	) {
		ArenaCommand.createArena(context, arenaID, arenaPassword, arenaRemark)
	}
}
