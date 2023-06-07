package ru.ifmo.storage

import ru.ifmo.db.Driver
import ru.ifmo.models.Event

class EventStorage {

    suspend fun readEvents(): Array<Event>{
        var events: Array<Event> = arrayOf()
        val sql = "SELECT * FROM events;"

        val connection = Driver.getConnection()
        val statement = connection.prepareStatement(sql)
        val results = statement.executeQuery()

        while(results.next()){
            events += Event(
                results.getLong(1),
                results.getString(2),
                results.getDouble(6),
                results.getDouble(7),
                results.getLong(4),
                results.getLong(5),
                results.getInt(3)
            )
        }
        return events;
    }

}