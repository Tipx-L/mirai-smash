package io.github.tipx_l.miraismash

import io.github.tipx_l.miraismash.command.CreateArenaCommand
import io.github.tipx_l.miraismash.command.ShowAllArenasCommand
import io.github.tipx_l.miraismash.command.ShutDownArenaCommand
import io.github.tipx_l.miraismash.smash.Arena
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info

object MiraiSmash : KotlinPlugin(JvmPluginDescription(
	id = "io.github.tipx_l.miraismash",
	name = "Mirai Smash",
	version = "0.1.0",
) {
	author("Tipx-L")
}) {
	val arenaMap = HashMap<Long, Arena>()
	override fun onEnable() {
		CommandManager.registerCommand(CreateArenaCommand())
		CommandManager.registerCommand(ShowAllArenasCommand())
		CommandManager.registerCommand(ShutDownArenaCommand())
		logger.info { "Mirai Smash已加载" }
	}
}
