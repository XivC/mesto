package ru.ifmo.services

import io.ktor.http.*
import ru.ifmo.models.Event
import ru.ifmo.models.Location
import ru.ifmo.storage.EventStorage

class EventService {
    private val eventStorage = EventStorage()

    suspend fun getAllEvents(): List<Event> = eventStorage.readEvents()

    suspend fun getEventById(id: Int): Event? = eventStorage.readEvents().find { it.id == id }

    suspend fun addEvent(event: Event): Event = eventStorage.createEvent(event)

    suspend fun updateEvent(event: Event): Event = eventStorage.updateEvent(event)

    suspend fun deleteEvent(id: Int): Boolean = eventStorage.deleteEvent(id)
}
