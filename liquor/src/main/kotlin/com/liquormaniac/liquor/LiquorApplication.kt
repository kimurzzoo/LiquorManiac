package com.liquormaniac.liquor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LiquorApplication

fun main(args: Array<String>) {
	runApplication<LiquorApplication>(*args)
}
