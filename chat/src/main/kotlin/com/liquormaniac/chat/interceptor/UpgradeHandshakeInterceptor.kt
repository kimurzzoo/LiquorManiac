package com.liquormaniac.chat.interceptor

import com.liquormaniac.common.client.client_util_dep.jwt.JwtResolver
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor

@Component
class UpgradeHandshakeInterceptor(private val jwtResolver: JwtResolver) : HttpSessionHandshakeInterceptor() {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        val uriSplit = request.uri.toString().split("?secondToken=")
        if(uriSplit.size != 2)
        {
            return false
        }

        return jwtResolver.validateToken(uriSplit[1])
    }
}