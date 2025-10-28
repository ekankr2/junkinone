package com.example.junkinone.dog.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * Dog Breeds API
 * Submitted by: @student_sarah
 * GitHub: https://github.com/student_sarah/dog-breeds-api
 */
@RestController
@RequestMapping("/api/dogbreeds")
@Tag(name = "Dog Breeds", description = "Dog breed information API (Student Project by @student_sarah)")
class DogBreedsController {

    private val breeds = mapOf(
        "golden-retriever" to mapOf(
            "name" to "Golden Retriever",
            "size" to "Large",
            "temperament" to "Friendly, Intelligent, Devoted"
        ),
        "bulldog" to mapOf(
            "name" to "Bulldog",
            "size" to "Medium",
            "temperament" to "Calm, Courageous, Friendly"
        ),
        "beagle" to mapOf(
            "name" to "Beagle",
            "size" to "Small",
            "temperament" to "Gentle, Even-tempered, Determined"
        )
    )

    @GetMapping
    fun getAllBreeds() = mapOf(
        "breeds" to breeds.keys,
        "count" to breeds.size
    )

    @GetMapping("/{breedId}")
    fun getBreed(@PathVariable breedId: String) =
        breeds[breedId] ?: mapOf("error" to "Breed not found")

    @GetMapping("/random")
    fun getRandomBreed() = breeds.values.random()
}