package com.liquormaniac.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = [
	"com.liquormaniac.chat",
	"com.liquormaniac.common.core.core_web",
	"com.liquormaniac.common.client.client_util_dep",
	"com.liquormaniac.common.domain.domain_chat"])
@EntityScan(basePackages = ["com.liquormaniac.common.domain.domain_chat.entity"])
@EnableJpaRepositories(basePackages = ["com.liquormaniac.common.domain.domain_chat.repository"])
@EnableJpaAuditing
class ChatApplication

fun main(args: Array<String>) {
	runApplication<ChatApplication>(*args)
}
