plugins {

}

dependencies {
    api(project(":common:domain:domain-core"))
    runtimeOnly("com.mysql:mysql-connector-j")
    api("org.springframework.boot:spring-boot-starter-security")
    api(project(":common:others:redis-config"))
    api(project(":common:client:client-util-dep"))
}


