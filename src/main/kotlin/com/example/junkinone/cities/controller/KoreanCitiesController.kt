package com.example.junkinone.cities.controller

import org.springframework.web.bind.annotation.*

/**
 * Korean Cities API
 * Submitted by: @student_jimin
 * GitHub: https://github.com/student_jimin/korean-cities
 */
@RestController
@RequestMapping("/api/korean-cities")
class KoreanCitiesController {

    private val cities = listOf(
        mapOf(
            "name" to "Seoul",
            "nameKr" to "서울",
            "population" to 9776000,
            "region" to "Capital Area"
        ),
        mapOf(
            "name" to "Busan",
            "nameKr" to "부산",
            "population" to 3413000,
            "region" to "Yeongnam"
        ),
        mapOf(
            "name" to "Incheon",
            "nameKr" to "인천",
            "population" to 2948000,
            "region" to "Capital Area"
        ),
        mapOf(
            "name" to "Daegu",
            "nameKr" to "대구",
            "population" to 2427000,
            "region" to "Yeongnam"
        )
    )

    @GetMapping
    fun getAllCities() = mapOf(
        "cities" to cities,
        "count" to cities.size
    )

    @GetMapping("/random")
    fun getRandomCity() = cities.random()

    @GetMapping("/region/{region}")
    fun getCitiesByRegion(@PathVariable region: String) = mapOf(
        "region" to region,
        "cities" to cities.filter { it["region"] == region }
    )
}