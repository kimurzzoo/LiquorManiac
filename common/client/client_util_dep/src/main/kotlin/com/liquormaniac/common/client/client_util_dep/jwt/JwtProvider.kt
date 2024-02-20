package com.liquormaniac.common.client.client_util_dep.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.security.Key
import java.security.PrivateKey
import java.util.*

@Component
class JwtProvider(private val resourceLoader: ResourceLoader) {

    @Value("\${auth.key.private}")
    private var privateKeyPath : String = ""

    private lateinit var privatekey : PrivateKey

    companion object
    {
        const val accessTokenValidTime = 30 * 60 * 1000L
        const val refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L
    }

    private var realkey : Key? = null

    @PostConstruct
    protected fun init() {
        val privateKeyResource : Resource = resourceLoader.getResource(privateKeyPath)
        val tempprivatekey = privateKey(privateKeyResource.file.toPath())
        if(tempprivatekey == null)
        {
            System.out.println("key is not initiated")
        }
        else
        {
            privatekey = tempprivatekey
        }
    }

    fun createAccessToken(username : String, indicator: String, role : String): String {
        val claims: Claims = Jwts.claims().setSubject(username) // JWT payload 에 저장되는 정보단위
        claims["indicator"] = indicator
        claims["role"] = role
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

    fun createRefreshToken(indicator : String) : String
    {
        val claims: Claims = Jwts.claims().setSubject(indicator)
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