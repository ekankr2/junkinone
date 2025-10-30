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
                        ## 🎓 토이 프로젝트 만들 때 필요한 API 모음

                        학생 개발자들이 토이 프로젝트를 만들 때 필요한 유용한 API들을 제공합니다.

                        - 🎲 **더미 데이터**: 한국 이름, 주소, 전화번호 등 테스트 데이터 생성
                        - 🇰🇷 **한국 데이터**: 공휴일, 은행 코드, 대학교 목록 등
                        - 🔧 **유틸리티**: 검증기, 변환기, 생성기 등

                        모든 API는 무료이며 인증이 필요 없습니다.
                    """.trimIndent())
                    .contact(
                        Contact()
                            .name("Junkinone")
                            .email("ekankr2@gmail.com")
                            .url("https://junkinone.com")
                    )
            )
            .servers(
                listOf(
                    Server()
                        .url("http://localhost:8080")
                        .description("Local Development"),
                    Server()
                        .url("https://api.junkinone.com")
                        .description("Production")
                )
            )
    }
}