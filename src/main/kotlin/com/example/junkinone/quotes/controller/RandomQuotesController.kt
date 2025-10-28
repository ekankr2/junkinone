package com.example.junkinone.quotes.controller

import org.springframework.web.bind.annotation.*

/**
 * Random Quotes API
 * Submitted by: @student_mike
 * GitHub: https://github.com/student_mike/motivational-quotes
 */
@RestController
@RequestMapping("/api/quotes")
class RandomQuotesController {

    private val quotes = listOf(
        mapOf(
            "text" to "The only way to do great work is to love what you do.",
            "author" to "Steve Jobs"
        ),
        mapOf(
            "text" to "Innovation distinguishes between a leader and a follower.",
            "author" to "Steve Jobs"
        ),
        mapOf(
            "text" to "Code is like humor. When you have to explain it, it's bad.",
            "author" to "Cory House"
        ),
        mapOf(
            "text" to "First, solve the problem. Then, write the code.",
            "author" to "John Johnson"
        )
    )

    @GetMapping("/random")
    fun getRandomQuote() = quotes.random()

    @GetMapping("/all")
    fun getAllQuotes() = mapOf(
        "quotes" to quotes,
        "count" to quotes.size
    )

    @GetMapping("/daily")
    fun getDailyQuote() = mapOf(
        "quote" to quotes[0],
        "date" to java.time.LocalDate.now()
    )
}