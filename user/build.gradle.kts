import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

}

dependencies {
	api(project(":common:domain:domain-user"))
	api(project(":common:core:core-web"))
	api(project(":common:client:client-util-dep"))
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.kafka:spring-kafka")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
