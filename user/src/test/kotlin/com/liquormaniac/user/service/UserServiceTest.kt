package com.liquormaniac.user.service

import com.liquormaniac.common.client.client_util_dep.jwt.JwtResolver
import com.liquormaniac.common.domain.domain_user.redis_repository.UserStatusRepository
import com.liquormaniac.common.domain.domain_user.redis_repository.VerificationCodeRepository
import com.liquormaniac.common.domain.domain_user.repository.UserRepository
import com.liquormaniac.user.dto.LoginDTO
import com.liquormaniac.user.dto.RegisterDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var userService : UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var jwtResolver: JwtResolver

    @Autowired
    private lateinit var userStatusRepository: UserStatusRepository

    @Autowired
    private lateinit var verificationCodeRepository: VerificationCodeRepository

    private val email = "kimurzzoo@gmail.com"

    @BeforeEach
    fun test_user()
    {
        val registerDTO : RegisterDTO = RegisterDTO("kimurzzoo@gmail.com", "159456789", "159456789", "달쌈", 1L)
        val response = userService.register(registerDTO)
    }

    @Test
    @Transactional
    fun test_register()
    {
        val registerDTO : RegisterDTO = RegisterDTO("kimurzzzoo@gmail.com", "q1w2e3r4", "q1w2e3r4", "별쌈", 2L)
        val response = userService.register(registerDTO)
        Assertions.assertEquals(200, response.code)

        val user = userRepository.findByEmailAddress(registerDTO.emailAddress)
        Assertions.assertEquals(registerDTO.nickName, user?.nickname)
    }

    @Test
    @Transactional
    fun test_withdrawal()
    {
        var user = userRepository.findByEmailAddress(email)
        Assertions.assertNotNull(user)
        val response = userService.withdrawal(user!!.id!!)
        Assertions.assertEquals(200, response.code)

        user = userRepository.findByEmailAddress(email)
        Assertions.assertNull(user)
    }

    @Test
    @Transactional
    fun test_login_and_logout()
    {
        val loginDTO = LoginDTO(email, "159456789")
        val response = userService.login(loginDTO)
        Assertions.assertEquals(200, response.code)

        val accessToken = response.data?.accessToken
        val refreshToken = response.data?.refreshToken

        Assertions.assertTrue(jwtResolver.validateToken(accessToken))

        val user = userRepository.findByEmailAddress(email)
        Assertions.assertNotNull(user)
        Assertions.assertEquals(user!!.id.toString(), accessToken?.let { jwtResolver.getUsername(it) })

        val logoutResponse = refreshToken?.let { userService.logout(user.id!!, it) }
        Assertions.assertEquals(200, logoutResponse?.code)

        val userStatus = userStatusRepository.findByEmail(loginDTO.emailAddress)
        Assertions.assertTrue(userStatus.isEmpty())
    }

    @Test
    @Transactional
    fun test_sendVerificationMail_and_verification()
    {
        val user = userRepository.findByEmailAddress(email)
        Assertions.assertNotNull(user)

        val response = userService.sendVerificationMail(user!!.id!!)
        Assertions.assertEquals(200, response.code)

        val verificationCodeOptional = verificationCodeRepository.findById(email)
        Assertions.assertNotNull(verificationCodeOptional)

        val verificationCode = verificationCodeOptional.get()
        Assertions.assertEquals(email, verificationCode.id)

        val code = verificationCode.code

        val verificationResponse = userService.verification(user!!.id!!, code)
        Assertions.assertEquals(200, verificationResponse.code)

        Assertions.assertFalse(verificationCodeRepository.existsById(email))
    }

    @Test
    @Transactional
    fun test_login_and_reissue()
    {
        val loginDTO = LoginDTO(email, "159456789")
        val response = userService.login(loginDTO)
        Assertions.assertEquals(200, response.code)

        val accessToken = response.data?.accessToken
        val refreshToken = response.data?.refreshToken

        Assertions.assertTrue(jwtResolver.validateToken(accessToken))

        val user = userRepository.findByEmailAddress(email)
        Assertions.assertNotNull(user)
        Assertions.assertEquals(user!!.id.toString(), accessToken?.let { jwtResolver.getUsername(it) })

        val reissueResponse = refreshToken?.let { accessToken?.let { it1 -> userService.reissue(it1, it) } }
        Assertions.assertEquals(200, reissueResponse?.code)

        val newAccessToken = reissueResponse?.data?.accessToken
        val newRefreshToken = reissueResponse?.data?.refreshToken
        Assertions.assertTrue(jwtResolver.validateToken(newAccessToken))
        Assertions.assertEquals(user.id.toString(), newAccessToken?.let {jwtResolver.getUsername(it)})
    }

    @Test
    @Transactional
    fun test_login_and_changeNickname_and_changePw()
    {
        val loginDTO = LoginDTO(email, "159456789")
        val response = userService.login(loginDTO)
        Assertions.assertEquals(200, response.code)

        val accessToken = response.data?.accessToken
        val refreshToken = response.data?.refreshToken

        Assertions.assertTrue(jwtResolver.validateToken(accessToken))
        val user = userRepository.findByEmailAddress(loginDTO.emailAddress)
        Assertions.assertEquals(user!!.id.toString(), accessToken?.let { jwtResolver.getUsername(it) })

        val changeNicknameResponse = userService.changeNickname(user.id!!, "해쌈")
        Assertions.assertEquals(200, changeNicknameResponse.code)

        val changePwResponse = refreshToken?.let { userService.changePw(user.id!!, it, loginDTO.password, "123456", "123456") }
        Assertions.assertEquals(200, changePwResponse?.code)

        val userStatus = refreshToken?.let { userStatusRepository.findById(it) }
        Assertions.assertNotNull(userStatus)
        userStatus?.isEmpty()?.let { Assertions.assertTrue(it) }

        Assertions.assertNotNull(user)
        Assertions.assertEquals("해쌈", user?.nickname)
        Assertions.assertEquals("123456", user?.m_password)


    }
}