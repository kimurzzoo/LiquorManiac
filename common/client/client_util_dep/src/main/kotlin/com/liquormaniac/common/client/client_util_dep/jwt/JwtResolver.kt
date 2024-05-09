package com.liquormaniac.common.client.client_util_dep.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.io.InputStream
import java.security.PublicKey
import java.util.*

@Component
class JwtResolver(private val resourceLoader: ResourceLoader) {
    @Value("\${auth.key.public}")
    private var publicKeyPath : String = ""

    private lateinit var publicKey : PublicKey

    private val objectMapper : ObjectMapper = ObjectMapper()

    @PostConstruct
    protected fun init() {
        val publicKeyInputStream : InputStream = ClassPathResource(publicKeyPath).inputStream
        val temppublickey = publicKey(publicKeyInputStream)
        if(temppublickey == null)
        {
            System.out.println("key is not initiated")
        }
        else
        {
            publicKey = temppublickey
        }
    }

    fun getClaims(token: String) : Claims
    {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).body
    }

    fun getClaimsFromExpiredToken(token : String) : Claims?
    {
        try {
            return getClaims(token)
        }
        catch (e : ExpiredJwtException)
        {
            val tokenSplit = token.split(".")
            val payloadJsonString = Base64.getDecoder().decode(tokenSplit[1]).contentToString()
            return getClaimsFromPayload(payloadJsonString)
        }
        catch (e : Exception)
        {
            return null
        }
    }

    fun getClaimsFromPayload(payloadJsonString : String) : Claims
    {
        val payloadMap = objectMapper.readValue(payloadJsonString, HashMap::class.java)
        val claims = Jwts.claims().setSubject(payloadMap["sub"].toString())
        claims["indicator"] = payloadMap["indicator"].toString()
        claims["role"] = payloadMap["role"].toString()
        return claims
    }

    fun getUsername(token : String) : String
    {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).body.subject
    }

    fun validateToken(jwtToken: String?): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jwtToken)
            if(claims.header.algorithm != "RS256")
            {
                false
            }
            else !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun bearerToken(jwt : String?) : String
    {
        try {
            if(jwt == null)
            {
                return ""
            }
            else
            {
                if(jwt.substring(0, 7) == "Bearer ")
                {
                    return jwt.substring(7)
                }
                else
                {
                    return ""
                }
            }
        }
        catch (e : Exception)
        {
            return ""
        }
    }
}