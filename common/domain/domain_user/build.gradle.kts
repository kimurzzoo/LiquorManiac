plugins {

}

dependencies {
    api(project(":common:domain:domain_core"))
    runtimeOnly("com.mysql:mysql-connector-j")
    api("org.springframework.boot:spring-boot-starter-security")
    api(project(":common:others:redis_config"))
    api(project(":common:client:client_util_dep"))
}


