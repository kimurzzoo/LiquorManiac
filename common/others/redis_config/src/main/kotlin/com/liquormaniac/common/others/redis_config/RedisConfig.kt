package com.liquormaniac.common.others.redis_config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
class RedisConfig {

    @Value("\${spring.redis.host}")
    private var host: String = ""

    @Value("\${spring.redis.port}")
    private var port = 0

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory
    {
        return LettuceConnectionFactory(host, port)
    }
}