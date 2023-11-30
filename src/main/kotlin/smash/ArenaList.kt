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

		val added = super.add(element)

		if (added) timeOutMap[element] = MiraiSmash.launch {
			delay(timeOut - timeElapsed)
			remove(element)
		}

		return added
	}

	fun shutDownArena(sender: CommandSender): Boolean {
		val user = sender.user ?: return false
		val groupID = sender.getGroupOrNull()?.id ?: return false
		val arena = find {
			val testingSender = it.context.sender
			val userID = testingSender.user?.id ?: return@find false

			if (userID != user.id) return@find false

			val testingGroupID = testingSender.getGroupOrNull()?.id ?: return@find false
			testingGroupID == groupID
		} ?: return false
		timeOutMap[arena]?.cancel()
		return remove(arena)
	}
}
