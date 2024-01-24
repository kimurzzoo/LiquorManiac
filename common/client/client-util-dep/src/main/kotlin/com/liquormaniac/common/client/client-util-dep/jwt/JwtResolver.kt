package com.liquormaniac.common.client.`client-util-dep`.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.sql.Timestamp
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class JwtResolver {
    @Value("\${auth.secret}")
    private var secretkey : String = ""

    companion object
    {
        const val accessTokenValidTime = 30 * 60 * 1000L
        const val refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L
    }

    private var realkey : Key? = null

    fun getClaims(token: String) : Claims
    {
        return Jwts.parserBuilder().setSigningKey(realkey).build().parseClaimsJws(token).body
    }

    fun getUsername(token : String) : String
    {
        return Jwts.parserBuilder().setSigningKey(realkey).build().parseClaimsJws(token).body.subject
    }

    fun validateToken(jwtToken: String?): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(realkey).build().parseClaimsJws(jwtToken)
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