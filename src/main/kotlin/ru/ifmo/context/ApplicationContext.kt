package ru.ifmo.context

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default

object ApplicationContext {
    private lateinit var dbConnectionUrl: String
    private var dbConnectionsPoolSize: Int = 10
    private val parser = ArgParser("server")

    fun init(args: Array<String>) {

        val poolSize by parser.option(ArgType.Int, fullName = "pool-size").default(10)
        parser.parse(args)
        this.dbConnectionUrl = System.getenv("DB_CONNECTION")
        this.dbConnectionsPoolSize = poolSize
    }

    fun getDbConnectionUrl() : String {
        return this.dbConnectionUrl
    }

    fun getDbConnectionPoolSize() : Int {
        return this.dbConnectionsPoolSize
    }

}