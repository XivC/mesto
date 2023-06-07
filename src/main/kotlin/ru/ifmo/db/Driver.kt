package ru.ifmo.db


import kotlinx.cli.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Semaphore
import java.sql.Connection
import java.sql.DriverManager

object Driver {
    private lateinit var semaphore: Semaphore
    private var poolSize: Int = 0
    private lateinit var connectionString: String
    private lateinit var scope: CoroutineScope

    fun init(args: Array<String>){
        val parser = ArgParser("server")
        val poolSize by parser.option(ArgType.Int, fullName = "pool-size").default(10)
        val connectionString by parser.option(ArgType.String, fullName = "db-connection").required()
        parser.parse(args)

        this.poolSize = poolSize
        this.connectionString = connectionString
        this.semaphore = Semaphore(this.poolSize)
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
        return DriverManager.getConnection(connectionString)
    }
}