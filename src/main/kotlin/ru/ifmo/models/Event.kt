package ru.ifmo.models

import kotlinx.serialization.Serializable

@Serializable
data class Event(

    val id: Long,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val startDate: Long,
    val endDate: Long?,
    val maxParticipants: Int?,
)