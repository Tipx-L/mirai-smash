package io.github.tipx_l.miraismash.smash

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Arena(
	val groupID: Long,
	val userID: Long,
	var arenaID: String,
	val arenaPassword: String = "",
	val arenaRemark: String = "",
	val creationTime: Instant = Clock.System.now()
)
