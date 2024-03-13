package com.liquormaniac.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@SpringBootApplication(scanBasePackages = [
	"com.liquormaniac.user",
	"com.liquormaniac.common.core.core_web",
	"com.liquormaniac.common.domain.domain_user",
	"com.liquormaniac.common.client.client_util_dep",
	"com.liquormaniac.common.others.redis_config"])
@EntityScan(basePackages = ["com.liquormaniac.common.domain.domain_user.entity"])
@EnableJpaRepositories(basePackages = ["com.liquormaniac.common.domain.domain_user.repository"])
@EnableRedisRepositories(basePackages = ["com.liquormaniac.common.domain.domain_user.redis_repository"])
@EnableJpaAuditing
class UserApplication
fun main(args: Array<String>) {
	runApplication<UserApplication>(*args)
}


