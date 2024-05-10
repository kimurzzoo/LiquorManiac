package com.liquormaniac.chat.controller

import com.liquormaniac.chat.dto.ChatMessageDTO
import com.liquormaniac.chat.service.ChatService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.RestController

/**
 * @author         : 김영호
 * @date           : 24. 5. 9.
 * @description    :
 */

@RestController
class ChatController(private val chatService: ChatService,
                     private val messagingTemplate : SimpMessageSendingOperations) {

    @MessageMapping("/message/{roomId}")
    fun message(message: ChatMessageDTO, messageHeaderAccessor : SimpMessageHeaderAccessor, @DestinationVariable("roomId") roomId : Long)
    {
        try {
            val userId : Long = messageHeaderAccessor.sessionAttributes?.get("userId") as Long
            chatService.sendMessage(userId, roomId, message.message)
        }
        catch (e : Exception)
        {
            //TODO 로그 남기기
        }
    }
}