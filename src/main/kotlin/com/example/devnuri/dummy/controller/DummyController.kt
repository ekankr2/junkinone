package com.example.devnuri.dummy.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Base controller for all dummy data APIs
 * All dummy controllers should inherit from this to get the /dummy prefix
 */
@RestController
@RequestMapping("/dummy")
@Tag(name = "더미 데이터", description = "테스트 및 개발용 랜덤 더미 데이터 생성")
abstract class DummyController