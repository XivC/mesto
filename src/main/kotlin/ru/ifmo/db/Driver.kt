package ru.ifmo.db


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Semaphore
import ru.ifmo.context.ApplicationContext
import java.sql.Connection
import java.sql.DriverManager


object Driver {
    private lateinit var semaphore: Semaphore
    private lateinit var scope: CoroutineScope

    fun init(){

        this.semaphore = Semaphore(ApplicationContext.getDbConnectionPoolSize())
        this.scope = CoroutineScope(Dispatchers.Default)

    }
    suspend fun getConnection(): Connection {
        return CoroutineScope(Dispatchers.IO).async {
            semaphore.acquire()
            try {
                createConnection()
            } finally {
                semaphore.release()
            }
        }.await()
    }

    private fun createConnection(): Connection {
        return DriverManager.getConnection(ApplicationContext.getDbConnectionUrl())
    }
}