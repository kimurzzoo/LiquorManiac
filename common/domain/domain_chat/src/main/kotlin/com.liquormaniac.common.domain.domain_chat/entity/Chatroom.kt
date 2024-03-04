package com.liquormaniac.common.domain.domain_chat.entity

import com.liquormaniac.common.domain.domain_core.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name="chatroom")
class Chatroom(userId1 : Long, isUser1 : Boolean = true, userId2 : Long, isUser2 : Boolean = true) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "user_id_1", columnDefinition = "bigint", nullable = false)
    var userId1 : Long = userId1

    @Column(name = "is_user_1", nullable = false)
    var isUser1 : Boolean = isUser1

    @Column(name = "user_id_2", columnDefinition = "bigint", nullable = false)
    var userId2 : Long = userId2

    @Column(name = "is_user_2", nullable = false)
    var isUser2 : Boolean = isUser2
}