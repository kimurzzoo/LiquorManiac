package com.liquormaniac.common.domain.domain_user.entity

import com.liquormaniac.common.client.client_util_dep.jwt.JwtProvider.Companion.refreshTokenValidTime
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "user_status", timeToLive = (refreshTokenValidTime / 1000))
class UserStatus(
    @Id @Indexed val id : String, //refresh token
    @Indexed val indicator: String,
    @Indexed val email: String)