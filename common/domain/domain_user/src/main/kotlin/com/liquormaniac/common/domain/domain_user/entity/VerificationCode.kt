package com.liquormaniac.common.domain.domain_user.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "user_status", timeToLive = 5 * 60)
class VerificationCode(
    @Id @Indexed val id : String, // email
    @Indexed val code : String
)