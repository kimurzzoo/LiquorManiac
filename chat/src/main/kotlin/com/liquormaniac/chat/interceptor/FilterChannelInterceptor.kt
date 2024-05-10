package com.liquormaniac.chat.interceptor

import com.liquormaniac.common.client.client_util_dep.jwt.JwtResolver
import com.liquormaniac.common.domain.domain_chat.entity.Chatroom
import com.liquormaniac.common.domain.domain_chat.repository.ChatroomRepository
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand.*
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class FilterChannelInterceptor(private val jwtResolver: JwtResolver,
                               private val chatroomRepository: ChatroomRepository) : ChannelInterceptor {
    private fun isUserNotInRoom(userId : Long, chatroom : Chatroom) : Boolean
    {
        if(chatroom.userId1 != userId && chatroom.userId2 != userId)
        {
            return true
        }

        if(chatroom.userId1 == userId && !chatroom.isUser1)
        {
            return true
        }

        if(chatroom.userId2 == userId && !chatroom.isUser2)
        {
            return true
        }

        return false
    }

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val headerAccessor = StompHeaderAccessor.wrap(message)


        val command = headerAccessor.command ?: return null // TODO return null을 하는 것이 맞나? throw exception이 나을 수도 있다

        val accessToken = headerAccessor.getFirstNativeHeader("Authorization") ?: return null

        when(command){
            CONNECT -> {
                try {
                    if(!jwtResolver.validateToken(accessToken))
                    {
                        return null
                    }
                    val userId = jwtResolver.getUsername(accessToken).toLong()
                    headerAccessor.sessionAttributes?.set("userId", userId)
                }
                catch (e : Exception)
                {
                    return null
                }
            }
            SUBSCRIBE -> {
                try {
                    val userId : Long = headerAccessor.sessionAttributes?.get("userId") as Long
                    val roomId = (headerAccessor.getHeader("simpDestination") as String).replace("/sub/message/", "").toLong()

                    val chatroom = chatroomRepository.findById(roomId).orElse(null)

                    if(chatroom == null || isUserNotInRoom(userId, chatroom))
                    {
                        return null
                    }
                }
                catch (e : Exception)
                {
                    return null
                }
            }
            SEND -> {
                try {
                    val userId : Long = headerAccessor.sessionAttributes?.get("userId") as Long
                    val roomId = (headerAccessor.getHeader("simpDestination") as String).replace("/pub/message/", "").toLong()

                    val chatroom = chatroomRepository.findById(roomId).orElse(null)

                    if(chatroom == null || isUserNotInRoom(userId, chatroom))
                    {
                        return null
                    }
                }
                catch (e : Exception)
                {
                    return null
                }
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