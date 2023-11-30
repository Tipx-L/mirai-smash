package io.github.tipx_l.miraismash.smash

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
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

		val arena = find {
			it.groupID == element.groupID && it.userID == element.userID
		}

		if (arena != null) {
			timeOutMap.remove(arena)?.cancel()
			remove(arena)
		}

		val added = super.add(element)
		timeOutMap[element] = MiraiSmash.launch {
			delay(timeOut - timeElapsed)
			remove(element)
		}
		MiraiSmashData.arenas = this
		return added
	}

	fun shutDownArena(sender: CommandSender): Boolean {
		val groupID = sender.getGroupOrNull()?.id ?: return false
		val userID = sender.user?.id ?: return false
		val arena = find {
			it.groupID == groupID && it.userID == userID
		} ?: return false
		timeOutMap.remove(arena)?.cancel()
		val removed = remove(arena)
		MiraiSmashData.arenas = this
		return removed
	}
}
