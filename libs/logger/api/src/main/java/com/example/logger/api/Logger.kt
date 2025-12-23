package com.example.logger.api

object Logger {
    interface Engine {
        fun debug(message: String)
    }

    private var engine: Engine? = null

    fun debug(message: String) {
        engine?.debug(message)
    }

    fun setEngine(engine: Engine) {
        this.engine = engine
    }
}

fun String.debug() = Logger.debug(this)
