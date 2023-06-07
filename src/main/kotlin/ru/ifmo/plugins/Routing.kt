package ru.ifmo.plugins

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

import ru.ifmo.models.Event
import ru.ifmo.services.EventService

fun Application.configureRouting() {
    routing {
        val eventHandler = ru.ifmo.handlers.EventHandler(EventService())
        eventRoutes(eventHandler)
    }

}
fun Route.eventRoutes(handler: ru.ifmo.handlers.EventHandler) {
    route("/events") {
        get {
            call.respond(handler.getAllEvents())
        }

        post {
            val event = call.receive<Event>()
            call.respond(HttpStatusCode.Created, handler.addEvent(event))
        }

        route("/{id}") {
            get {
                val id = call.parameters["id"]?.toInt()
                if (id != null) {
                    val event = handler.getEvent(id)
                    if (event != null) {
                        call.respond(event)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
            }

            put {
                val eventToUpdate = call.receive<Event>()
                call.respond(handler.updateEvent(eventToUpdate))
            }

            delete {
                val id = call.parameters["id"]?.toInt()
                if (id != null) {
                    handler.deleteEvent(id)
                    call.respond(HttpStatusCode.NoContent)
                }
            }
        }
    }
}