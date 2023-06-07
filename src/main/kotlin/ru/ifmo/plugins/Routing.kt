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
            handler.getAllEvents(call)
        }

        post {

              handler.addEvent(call)
        }

        route("/{id}") {
            get {
                handler.getEvent(call)
                }
            }

            put {
                handler.updateEvent(call)
            }

            delete {
                handler.deleteEvent(call)
                }
            }
        }
