package ru.ifmo.handlers

import ru.ifmo.models.Event
import ru.ifmo.services.EventService

class EventHandler(private val service: EventService) {
    suspend fun getAllEvents(): List<Event> = service.getAllEvents()

    suspend fun getEvent(id: Int): Event? = service.getEventById(id)

    suspend fun addEvent(event: Event): Event = service.addEvent(event)

    suspend fun updateEvent(event: Event): Event = service.updateEvent(event)

    suspend fun deleteEvent(id: Int): Boolean = service.deleteEvent(id)
}

