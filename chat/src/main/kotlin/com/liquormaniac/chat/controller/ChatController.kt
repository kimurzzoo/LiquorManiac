package com.liquormaniac.chat.controller

import com.liquormaniac.chat.dto.ChatMessageDTO
import com.liquormaniac.chat.service.ChatService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author         : 김영호
 * @date           : 24. 5. 9.
 * @description    :
 */

@RestController
class ChatController(private val chatService: ChatService) {
    @MessageMapping("/message/{roomId}")
    fun message(message: ChatMessageDTO, @DestinationVariable("roomId") roomId : Long)
    {
        chatService.sendMessage()
    }
}