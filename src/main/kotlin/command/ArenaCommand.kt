package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand

object ArenaCommand : SimpleCommand(MiraiSmash, "房间", description = "房间") {
	override val usage: String
		get() = "/房间 [房间 ID] [房间密码] [房间备注]"

	@Handler
	suspend fun arena(
		context: CommandContext, arenaID: String = "", arenaPassword: String = "", arenaRemark: String = ""
	) {
		if (arenaID.isEmpty()) ShowAllArenasCommand.showAllArenas(context)
		else CreateArenaCommand.createArena(context, arenaID, arenaPassword, arenaRemark)
	}
}
