package com.example.junkinone.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Junkinone API")
                    .version("1.0.0")
                    .description("""
                        ## 🎓 Free APIs from Bootcamp Students

                        부트캠프 학생들이 만든 API와 무료 공개 API를 수집하여 제공합니다.

                        - 🎯 **Student Projects**: Real APIs built by bootcamp students
                        - 🆓 **Free APIs**: Curated public APIs
                        - 📊 **Usage Stats**: Track your API usage for portfolio

                        All APIs are free to use. No authentication required.
                    """.trimIndent())
                    .contact(
                        Contact()
                            .name("Junkinone")
                            .email("ekankr2@gmail.com")
                            .url("https://apinuri.com")
                    )
            )
            .servers(
                listOf(
                    Server()
                        .url("http://localhost:8080")
                        .description("Local Development"),
                    Server()
                        .url("https://junkinone.com")
                        .description("Production")
                )
            )
    }
}