package ru.ifmo.storage

import io.ktor.network.sockets.*
import ru.ifmo.db.Driver
import ru.ifmo.db.Driver.getConnection
import ru.ifmo.models.Event
import ru.ifmo.models.Location
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class EventStorage {

    suspend fun readEvents(): List<Event> {
        val connection = getConnection()

        val statement = connection.prepareStatement("SELECT * FROM events e JOIN locations l ON e.location_id = l.id;")
        val results = statement.executeQuery()

        val events = mutableListOf<Event>()
        while (results.next()) {
            events.add(
                Event(
                    id = results.getInt("id"),
                    title = results.getString("title"),
                    startTime = results.getString("startTime"),
                    endTime = results.getString("endTime"),
                    maxParticipants = results.getInt("maxParticipants"),
                    location = Location(
                        latitude = results.getFloat("latitude"),
                        longitude = results.getFloat("longitude")
                    ),
                    needParticipatingApprove = results.getBoolean("needParticipatingApprove"),
                    tags = null
                )
            )
        }
        connection.close()
        return events
    }

    suspend fun createEvent(event: Event): Event {
        val connection = getConnection()
        try {
            connection.autoCommit = false
        var statement = connection.prepareStatement("INSERT INTO locations (latitude, longitude) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)
        statement.setFloat(1, event.location.latitude)
        statement.setFloat(2, event.location.longitude)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        var locationId = 0
        if (generatedKeys.next()) {
            locationId = generatedKeys.getInt(1)
        }

        statement = connection.prepareStatement("INSERT INTO events (title, startTime, endTime, maxParticipants, location_id, needParticipatingApprove, tags) VALUES (?, ?, ?, ?, ?, ?, ?)")
        statement.setString(1, event.title)
        statement.setString(2, event.startTime)
        statement.setString(3, event.endTime)
        statement.setInt(4, event.maxParticipants)
        statement.setInt(5, locationId)
        statement.setBoolean(6, event.needParticipatingApprove)
        statement.setString(7, event.tags?.joinToString(","))
        statement.executeUpdate()
            connection.commit()
        } catch (ex: SQLException) {
            connection.rollback()
        } finally {
            connection.autoCommit = true
            connection.close()
        }
        return event
    }

    suspend fun updateEvent(event: Event): Event {
        val connection = getConnection()
        try {
            connection.autoCommit = false
        var statement = connection.prepareStatement("UPDATE locations SET latitude = ?, longitude = ? WHERE id = (SELECT location_id FROM events WHERE id = ?)")
        statement.setFloat(1, event.location.latitude)
        statement.setFloat(2, event.location.longitude)
        statement.setInt(3, event.id)
        statement.executeUpdate()

        statement = connection.prepareStatement("UPDATE events SET title = ?, startTime = ?, endTime = ?, maxParticipants = ?, needParticipatingApprove = ?, tags = ? WHERE id = ?")
        statement.setString(1, event.title)
        statement.setString(2, event.startTime)
        statement.setString(3, event.endTime)
        statement.setInt(4, event.maxParticipants)
        statement.setBoolean(5, event.needParticipatingApprove)
        statement.setString(6, event.tags?.joinToString(","))
        statement.setInt(7, event.id)
        statement.executeUpdate()
        connection.commit()
    } catch (ex: SQLException) {
        connection.rollback()
    } finally {
        connection.autoCommit = true
        connection.close()
    }
        return event
    }

    suspend fun deleteEvent(id: Int): Boolean {
        val connection = getConnection()
        try {
            connection.autoCommit = false
        val statement = connection.prepareStatement("DELETE FROM events WHERE id = ?")
        statement.setInt(1, id)
            val affectedRows = statement.executeUpdate()
            connection.commit()
            return affectedRows > 0
        } catch (ex: SQLException) {
            connection.rollback()
        } finally {
            connection.autoCommit = true
            connection.close()

        }

        return false
    }

}
