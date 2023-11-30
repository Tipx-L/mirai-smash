package io.github.tipx_l.miraismash

import io.github.tipx_l.miraismash.smash.ArenaList
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object MiraiSmashData : AutoSavePluginData("mirai-smash-data") {
	val arenas: ArenaList by value()
}
