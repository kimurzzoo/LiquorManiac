package com.liquormaniac.common.domain.domain_user.redis_entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "user_status", timeToLive = 7 * 24 * 60 * 60)
class UserStatus(
    @Id @Indexed val id : String, //refresh token
    @Indexed val indicator: String,
    @Indexed val email: String)

// refresh token을 id로 해놓은 이유는 indicator에 따라 기기마다 refresh token이 다르기 때문이다.
// indicator를 id로 해놓을 수도 있으나 indicator가 정말 낮은 확률이지만 겹칠 수도 있기 때문에 이렇게 해놓았다.
// 그렇다고 저 낮은 확률을 로그인 때마다 매번 검증하자니 redis 부하를 쓸데없이 늘리는 것이라 생각했다.