package com.liquormaniac.user.service

import com.liquormaniac.user.dto.RegisterDTO
import com.liquormaniac.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class UserServiceTest(private val userService : UserService) {

    @Test
    fun test_register()
    {
        val registerDTO : RegisterDTO = RegisterDTO("kimurzzoo@gmail.com", "A1s2d3f4g%", "A1s2d3f4g%", "달쌈")
        val response = userService.register(registerDTO)

        Assertions.assertEquals(200, response.code)
    }
}