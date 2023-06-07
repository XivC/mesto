package ru.ifmo.handlers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.ifmo.models.Event
import ru.ifmo.services.EventService

class EventHandler(private val service: EventService) {
    suspend fun getAllEvents(call: ApplicationCall) = call.respond(service.getAllEvents())

    suspend fun getEvent(call: ApplicationCall) {
        val id = call.parameters["id"]?.toInt()
        if (id == null)
        {
            call.respond(HttpStatusCode.BadRequest)
            return
        }

        val event = service.getEventById(id)
        if (event != null) {
            call.respond(event)
            return
        }

        call.respond(HttpStatusCode.NotFound)
    }

    suspend fun addEvent(call: ApplicationCall)
    {
        val event = call.receive<Event>()
        call.respond(HttpStatusCode.Created,service.addEvent(event))
    }

    suspend fun updateEvent(call: ApplicationCall) {
        val event = call.receive<Event>()
        call.respond(service.updateEvent(event))
    }

    suspend fun deleteEvent(call: ApplicationCall) {
        val id = call.parameters["id"]?.toInt()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest)
            return
        }
        call.respond(service.deleteEvent(id))
    }
}
