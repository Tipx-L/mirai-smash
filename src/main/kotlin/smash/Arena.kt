package io.github.tipx_l.miraismash.smash

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import net.mamoe.mirai.console.command.CommandContext

data class Arena(
	val context: CommandContext,
	val arenaID: String,
	val arenaPassword: String = "",
	val arenaRemark: String = ""
) {
	val creationTime: Instant = Clock.System.now()
}
