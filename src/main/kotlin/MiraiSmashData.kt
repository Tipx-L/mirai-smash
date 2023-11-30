package io.github.tipx_l.miraismash

import io.github.tipx_l.miraismash.smash.Arena
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object MiraiSmashData : AutoSavePluginData("mirai-smash-data") {
	val arenas: MutableList<Arena> by value()
}
