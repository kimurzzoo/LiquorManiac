package com.liquormaniac.common.client.client_util_dep.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.security.PublicKey
import java.util.*

@Component
class JwtResolver(private val resourceLoader: ResourceLoader) {
    @Value("\${auth.key.public}")
    private var publicKeyPath : String = ""

    private lateinit var publicKey : PublicKey

    @PostConstruct
    protected fun init() {
        val publicKeyResource : Resource = resourceLoader.getResource(publicKeyPath)
        val temppublickey = publicKey(publicKeyResource.file.toPath())
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

    fun getUsername(token : String) : String
    {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).body.subject
    }

    fun validateToken(jwtToken: String?): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
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