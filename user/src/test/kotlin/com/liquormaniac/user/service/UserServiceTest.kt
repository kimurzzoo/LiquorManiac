package com.liquormaniac.user.service

import com.liquormaniac.common.client.client_util_dep.jwt.JwtResolver
import com.liquormaniac.common.domain.domain_user.repository.UserRepository
import com.liquormaniac.user.dto.LoginDTO
import com.liquormaniac.user.dto.RegisterDTO
import com.liquormaniac.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var userService : UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var jwtResolver: JwtResolver

    @BeforeEach
    fun test_user()
    {
        val registerDTO : RegisterDTO = RegisterDTO("kimurzzoo@gmail.com", "159456789", "159456789", "달쌈")
        val response = userService.register(registerDTO)
    }

    @Test
    @Transactional
    fun test_register()
    {
        val registerDTO : RegisterDTO = RegisterDTO("kimurzzzoo@gmail.com", "q1w2e3r4", "q1w2e3r4", "별쌈")
        val response = userService.register(registerDTO)
        Assertions.assertEquals(200, response.code)

        val user = userRepository.findByEmailAddress(registerDTO.emailAddress)
        Assertions.assertEquals(registerDTO.nickName, user?.nickname)
    }

    @Test
    @Transactional
    fun test_login()
    {
        val loginDTO = LoginDTO("kimurzzoo@gmail.com", "159456789")
        val response = userService.login(loginDTO)
        Assertions.assertEquals(200, response.code)

        val accessToken = response.data?.accessToken
        val refreshToken = response.data?.refreshToken
        System.out.println(accessToken)
        System.out.println(refreshToken)
        System.out.println(accessToken?.let { jwtResolver.getUsername(it) })

        Assertions.assertTrue(jwtResolver.validateToken(accessToken))
        Assertions.assertEquals(loginDTO.emailAddress, accessToken?.let { jwtResolver.getUsername(it) })
    }
}