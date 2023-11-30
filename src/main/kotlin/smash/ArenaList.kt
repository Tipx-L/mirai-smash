package io.github.tipx_l.miraismash.smash

import io.github.tipx_l.miraismash.MiraiSmash
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.getGroupOrNull
import kotlin.time.Duration.Companion.hours

class ArenaList : ArrayList<Arena>() {
	private val timeOutMap = HashMap<Arena, Job>()

	override fun add(element: Arena): Boolean {
		val timeElapsed = Clock.System.now() - element.creationTime
		val timeOut = 8.hours

		if (timeElapsed >= timeOut) return false

		timeOutMap[element] = MiraiSmash.launch {
			delay(timeOut - timeElapsed)
			remove(element)
		}

		return super.add(element)
	}

	fun shutDownArena(sender: CommandSender): Boolean {
		val user = sender.user ?: return false
		val groupID = sender.getGroupOrNull()?.id ?: return false
		val arena = find {
			val arenaSender = it.context.sender
			val userID = arenaSender.user?.id ?: return@find false

			if (userID != user.id) return@find false

			val arenaGroupID = arenaSender.getGroupOrNull()?.id ?: return@find false
			arenaGroupID == groupID
		} ?: return false
		timeOutMap.remove(arena)?.cancel()
		return remove(arena)
	}
}
