package com.liquormaniac.chat.kafka.producer

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ChatProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {
    fun publicMessage(message : String) // 그냥 예시임
    {
        kafkaTemplate.send("chat", message) //TODO topic 변경
    }
}