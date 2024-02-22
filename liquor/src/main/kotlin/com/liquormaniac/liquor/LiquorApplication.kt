package com.liquormaniac.liquor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.liquormaniac.liquor",
	"com.liquormaniac.common.domain.domain_liquor",
	"com.liquormaniac.common.core.core_liquor"])
class LiquorApplication

fun main(args: Array<String>) {
	runApplication<LiquorApplication>(*args)
}
