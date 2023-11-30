package io.github.tipx_l.miraismash.smash

import io.github.tipx_l.miraismash.MiraiSmash
import io.github.tipx_l.miraismash.MiraiSmashData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import net.mamoe.mirai.utils.info
import java.util.function.Predicate
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

		if (arena != null) remove(arena)

		val added = super.add(element)
		timeOutMap[element] = MiraiSmash.launch {
			delay(timeOut - timeElapsed)
			remove(element)
		}
		MiraiSmashData.arenas = this
		return added
	}

	override fun remove(element: Arena): Boolean {
		timeOutMap.remove(element)?.cancel()
		val remove = super.remove(element)
		MiraiSmashData.arenas = this
		return remove
	}

	override fun addAll(elements: Collection<Arena>): Boolean {
		val arenas = elements.filter {
			if (Clock.System.now() - it.creationTime >= 8.hours) return false

			val removingArena = find { arena ->
				arena.groupID == it.groupID && arena.userID == it.userID
			}

			if (removingArena != null) remove(removingArena)

			return true
		}
		MiraiSmash.logger.info { MiraiSmash.arenas.toString() }
		val added = super.addAll(arenas)
		arenas.forEach {
			timeOutMap[it] = MiraiSmash.launch {
				delay(8.hours - (Clock.System.now() - it.creationTime))
				remove(it)
			}
		}
		MiraiSmashData.arenas = this
		return added
	}

	override fun removeIf(filter: Predicate<in Arena>): Boolean {
		forEach {
			if (filter.test(it)) timeOutMap.remove(it)?.cancel()
		}
		val removed = super.removeIf(filter)
		MiraiSmashData.arenas = this
		return removed
	}
}
