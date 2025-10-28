package com.example.junkinone.cat.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * Cat Facts API
 * Submitted by: @student_john
 * GitHub: https://github.com/student_john/cat-facts
 */
@RestController
@RequestMapping("/api/catfacts")
@Tag(name = "Cat Facts", description = "Random cat facts API (Student Project by @student_john)")
class CatFactsController {

    private val facts = listOf(
        "Cats sleep 70% of their lives",
        "A group of cats is called a clowder",
        "Cats have over 20 vocalizations",
        "A cat's purr vibrates at 25-150 Hz",
        "Cats can rotate their ears 180 degrees"
    )

    @GetMapping("/random")
    @Operation(summary = "Get a random cat fact", description = "Returns a random interesting fact about cats")
    fun getRandomFact() = mapOf(
        "fact" to facts.random(),
        "category" to "cats"
    )

    @GetMapping("/all")
    @Operation(summary = "Get all cat facts", description = "Returns all available cat facts with count")
    fun getAllFacts() = mapOf(
        "facts" to facts,
        "count" to facts.size
    )
}