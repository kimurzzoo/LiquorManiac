package com.liquormaniac.user

import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.liquormaniac.common.core.core-web",
    "com.liquormaniac.common.domain.domain-user",
    "com.liquormaniac.common.client.client-util-dep",
    "com.liquormaniac.common.others.redis-config"])
class TestConfiguration {
}