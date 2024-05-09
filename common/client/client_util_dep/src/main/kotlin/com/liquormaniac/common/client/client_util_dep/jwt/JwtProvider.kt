package com.liquormaniac.common.client.client_util_dep.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.io.InputStream
import java.security.Key
import java.security.PrivateKey
import java.util.*

@Component
class JwtProvider(private val resourceLoader: ResourceLoader) {

    @Value("\${auth.key.private}")
    private var privateKeyPath : String = ""

    @Value("\${token.valid.access}")
    private var accessTokenValidTime : Long = 0L

    @Value("\${token.valid.refresh}")
    private var refreshTokenValidTime : Long = 0L

    private lateinit var privatekey : PrivateKey

    private var realkey : Key? = null

    @PostConstruct
    protected fun init() {
        val privateKeyInputStream : InputStream = ClassPathResource(privateKeyPath).inputStream
        val tempprivatekey = privateKey(privateKeyInputStream)
        if(tempprivatekey == null)
        {
            System.out.println("key is not initiated")
        }
        else
        {
            privatekey = tempprivatekey
        }
    }

    fun createAccessToken(userId : Long, indicator: String, chain : String, role : String): String {
        val claims: Claims = Jwts.claims().setSubject(userId.toString()) // JWT payload 에 저장되는 정보단위
        claims["indicator"] = indicator
        claims["role"] = role
        claims["chain"] = chain
        val now = Date()
        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "RS256")
            .setClaims(claims) // 정보 저장
            .setIssuedAt(now) // 토큰 발행 시간 정보
            .setExpiration(Date(now.time + accessTokenValidTime)) // set Expire Time
            .signWith(privatekey, SignatureAlgorithm.RS256)
            // signature 에 들어갈 secret값 세팅
            .compact()
    }

    fun createRefreshToken(indicator : String, chain : String) : String
    {
        val claims: Claims = Jwts.claims().setSubject(indicator)
        claims["chain"] = chain
        val now = Date()

        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "RS256")
            .setClaims(claims) // 정보 저장
            .setExpiration(Date(now.time + refreshTokenValidTime))
            .signWith(privatekey , SignatureAlgorithm.RS256)
            .compact()
    }
}