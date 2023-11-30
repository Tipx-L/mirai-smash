package io.github.tipx_l.miraismash

import io.github.tipx_l.miraismash.command.ArenaCommand
import io.github.tipx_l.miraismash.command.CreateArenaCommand
import io.github.tipx_l.miraismash.command.ShowAllArenasCommand
import io.github.tipx_l.miraismash.command.ShutDownArenaCommand
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info

object MiraiSmash : KotlinPlugin(JvmPluginDescription(
	id = "io.github.tipx_l.miraismash",
	name = "Mirai Smash",
	version = "0.1.3",
) {
	author("Tipx-L")
}) {
	override fun onEnable() {
		MiraiSmashData.reload()
		MiraiSmashData.arenas.forEach { logger.info { it.toString() } }
		CommandManager.registerCommand(ArenaCommand())
		CommandManager.registerCommand(CreateArenaCommand())
		CommandManager.registerCommand(ShowAllArenasCommand())
		CommandManager.registerCommand(ShutDownArenaCommand())
		logger.info { "╭┬─╮" }
		logger.info { "││ │ Mirai Smash" }
		logger.info { "├┼─┤ 已加载" }
		logger.info { "╰┴─╯" }
	}
}
