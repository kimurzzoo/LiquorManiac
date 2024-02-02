package com.liquormaniac.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

//@EntityScan(basePackages = ["com.liquormaniac.common.domain"])
@SpringBootApplication(scanBasePackages = ["com.liquormaniac.common.core.core-web", "com.liquormaniac.common.domain.domain-user", "com.liquormaniac.common.client.client-util-dep"])
class UserApplication

fun main(args: Array<String>) {
	runApplication<UserApplication>(*args)
}
