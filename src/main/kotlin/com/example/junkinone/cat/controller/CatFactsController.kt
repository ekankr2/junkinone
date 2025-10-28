package com.example.junkinone.cat.controller

import org.springframework.web.bind.annotation.*

/**
 * Cat Facts API
 * Submitted by: @student_john
 * GitHub: https://github.com/student_john/cat-facts
 */
@RestController
@RequestMapping("/api/catfacts")
class CatFactsController {

    private val facts = listOf(
        "Cats sleep 70% of their lives",
        "A group of cats is called a clowder",
        "Cats have over 20 vocalizations",
        "A cat's purr vibrates at 25-150 Hz",
        "Cats can rotate their ears 180 degrees"
    )

    @GetMapping("/random")
    fun getRandomFact() = mapOf(
        "fact" to facts.random(),
        "category" to "cats"
    )

    @GetMapping("/all")
    fun getAllFacts() = mapOf(
        "facts" to facts,
        "count" to facts.size
    )
}