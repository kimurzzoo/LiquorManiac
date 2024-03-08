package com.liquormaniac.chat.interceptor

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand.*
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class FilterChannelInterceptor : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val headerAccessor = StompHeaderAccessor.wrap(message)

        val command = headerAccessor.command ?: return null // TODO return null을 하는 것이 맞나? throw exception이 나을 수도 있다

        when(command){
            CONNECT -> {

            }
            SUBSCRIBE, SEND -> {

            }
            DISCONNECT -> {

            }
            UNSUBSCRIBE -> {

            }
            else -> TODO()
        }
        return super.preSend(message, channel)
    }
}