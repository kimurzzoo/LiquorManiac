package com.liquormaniac.chat.kafka.consumer

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ChatConsumer {
    @KafkaListener(id = "chat_message_listener", topics = ["chat"]) //TODO group id 바꾸고 topic도 적절하게 변경하기
    fun listener(data: Any?) {
        println(data)
    }
}