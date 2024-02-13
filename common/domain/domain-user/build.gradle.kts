plugins {

}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")
    api("org.springframework.boot:spring-boot-starter-security")
    api(project(":common:others:redis-config"))
    api(project(":common:client:client-util-dep"))
}


