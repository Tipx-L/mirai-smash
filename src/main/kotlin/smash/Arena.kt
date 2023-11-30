package io.github.tipx_l.miraismash.smash

import java.time.Instant

data class Arena(
	val arenaID: String,
	val arenaPassword: String = "",
	val arenaRemark: String = "",
	val creationTime: Instant = Instant.now()
)
