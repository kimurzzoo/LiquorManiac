package com.liquormaniac.common.domain.domain_chat.entity

import jakarta.persistence.*

@Entity
@Table(name="message")
class MessageImage(messageId : Long, fileName : String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "message_id", columnDefinition = "bigint", nullable = false)
    var messageId : Long = messageId

    @Column(name = "file_name", columnDefinition = "varchar(300)", nullable = false)
    var fileName : String = fileName
}