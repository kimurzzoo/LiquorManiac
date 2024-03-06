import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
}

dependencies {
	api(project(":common:domain:domain_chat"))
	api(project(":common:core:core_web"))
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:+")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}
