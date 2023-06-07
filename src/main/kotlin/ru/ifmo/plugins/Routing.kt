package ru.ifmo.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import ru.ifmo.handlers.EventHandler

fun Application.configureRouting() {
    routing {
        get("/events/") {
            EventHandler(call).handleReadEvents()
        }
    }
}
