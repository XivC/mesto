package ru.ifmo

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.flywaydb.core.Flyway
import ru.ifmo.context.ApplicationContext
import ru.ifmo.db.Driver
import ru.ifmo.plugins.*

fun main(args: Array<String>) {
    ApplicationContext.init(args)
    Driver.init()
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
