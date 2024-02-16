package com.liquormaniac.user.util

import com.liquormaniac.common.client.`client-util-dep`.jwt.JwtProvider
import com.liquormaniac.common.client.`client-util-dep`.jwt.JwtResolver
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class JwtTest {
    @Autowired
    private var jwtProvider: JwtProvider

    @Autowired
    private var jwtResolver: JwtResolver

    @Test
    fun jwtProvidingAndResolving()
    {
        val accessToken : String = jwtProvider.createAccessToken("kimurzzoo@gmail.com", UUID.randomUUID().toString(), "ROLE_USER")
        println(accessToken)

        Assertions.assertTrue(jwtResolver.validateToken(accessToken))
    }
}