
plugins {
}

dependencies {
	api(project(":common:domain:domain_liquor"))
	api(project(":common:core:core_web"))
	api(project(":common:core:core_liquor"))
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:+")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}