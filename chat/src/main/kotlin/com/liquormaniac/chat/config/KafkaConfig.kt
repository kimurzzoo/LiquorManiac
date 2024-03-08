package com.liquormaniac.chat.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import java.util.*
import kotlin.collections.HashMap

@EnableKafka
class KafkaConfig {
    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private val bootstrapAddress: String = ""

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs: MutableMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress!!
        return KafkaAdmin(configs)
    }

    private fun producerFactory() : ProducerFactory<String, String> {
        val configProps : HashMap<String, Any> = hashMapOf()
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress as Any)
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class)
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class)
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplate() : KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory());
    }

    private fun consumerFactory(): ConsumerFactory<String, String> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = OffsetResetStrategy.LATEST //.toString()
        props[ConsumerConfig.GROUP_ID_CONFIG] = UUID.randomUUID().toString()
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}