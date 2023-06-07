package ru.ifmo

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.ifmo.db.Driver
import ru.ifmo.plugins.*
import ru.ifmo.services.EventService

fun main(args: Array<String>) {
    Driver.init(args)
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()

}
