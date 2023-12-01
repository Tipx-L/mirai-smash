package io.github.tipx_l.miraismash.command

import io.github.tipx_l.miraismash.MiraiSmash
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand

class ShowAllArenasCommand : SimpleCommand(MiraiSmash, "查房", description = "查房") {
	override val usage: String
		get() = "/查房"

	@Handler
	suspend fun showAllArenas(context: CommandContext) {
		ArenaCommand.showAllArenas(context)
	}
}
