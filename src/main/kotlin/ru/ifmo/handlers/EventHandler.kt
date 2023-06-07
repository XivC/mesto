package ru.ifmo.handlers


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import ru.ifmo.models.Event
import ru.ifmo.storage.EventStorage

class EventHandler (private val call: ApplicationCall){

    suspend fun handleReadEvents(){
        call.respond(HttpStatusCode.OK, EventStorage().readEvents())

    }

}