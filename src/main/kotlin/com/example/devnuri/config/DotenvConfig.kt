package com.example.devnuri.config

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.DotenvException
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource

class DotenvConfig : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        try {
            val dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load()

            val properties = dotenv.entries()
                .associate { it.key to it.value }

            applicationContext.environment.propertySources.addFirst(
                MapPropertySource("dotenvProperties", properties)
            )
        } catch (e: DotenvException) {
            // .env file not found, continue without it
        }
    }
}