package com.liquormaniac.common.domain.domain_user.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "user_status", timeToLive = 7 * 24 * 60 * 60)
class UserStatus(
    @Id @Indexed val id : String, //refresh token
    @Indexed val indicator: String,
    @Indexed val email: String)