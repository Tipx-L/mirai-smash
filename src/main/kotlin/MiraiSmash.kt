package io.github.tipx_l.miraismash

import io.github.tipx_l.miraismash.command.ArenaCommand
import io.github.tipx_l.miraismash.command.CreateArenaCommand
import io.github.tipx_l.miraismash.command.ShowAllArenasCommand
import io.github.tipx_l.miraismash.command.ShutDownArenaCommand
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.utils.info
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

object MiraiSmash : KotlinPlugin(JvmPluginDescription(
	id = "io.github.tipx_l.miraismash",
	name = "Mirai Smash",
	version = "0.1.3",
) {
	author("Tipx-L")
}) {
	private val interval = 1.minutes
	private val timeOut = 5.hours
	override fun onEnable() {
		MiraiSmashData.reload()
		launch {
			while (true) {
				delay(interval)
				MiraiSmashData.arenas.removeIf { arena ->
					if (Clock.System.now() - arena.creationTime < timeOut) return@removeIf false

					Bot.instances.forEach {
						val group = it.getGroup(arena.groupID) ?: return@forEach
						val normalMember = group[arena.userID] ?: return@forEach
						runBlocking {
							group.sendMessage(buildMessageChain {
								+At(normalMember)
								+" 房间超过 "
								+timeOut.inWholeHours.toString()
								+" 小时未关，超时已关闭"
							})
						}
					}
					return@removeIf true
				}
			}
		}
		CommandManager.registerCommand(ArenaCommand())
		CommandManager.registerCommand(CreateArenaCommand())
		CommandManager.registerCommand(ShowAllArenasCommand())
		CommandManager.registerCommand(ShutDownArenaCommand())
		logger.info {
			"╭┬─╮"
		}
		logger.info {
			"││ │ Mirai Smash"
		}
		logger.info {
			"├┼─┤ 已加载"
		}
		logger.info {
			"╰┴─╯"
		}
	}
}
