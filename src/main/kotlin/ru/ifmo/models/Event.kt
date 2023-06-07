package ru.ifmo.models

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: Int,
    val title: String,
    val startTime: String,
    val endTime: String,
    val maxParticipants: Int,
    val location: Location,
    val needParticipatingApprove: Boolean,
    val tags: List<String>?
)

data class BaseEventParticipant(
    val id: Int,
    val status: String
)
@Serializable
data class Location(
    val latitude: Float,
    val longitude: Float
)