package com.liquormaniac.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = ["com.liquormaniac.common.core.core-web",
	"com.liquormaniac.common.domain.domain-user",
	"com.liquormaniac.common.client.client-util-dep",
	"com.liquormaniac.common.others.redis-config"])
class UserApplication

fun main(args: Array<String>) {
	runApplication<UserApplication>(*args)
}
