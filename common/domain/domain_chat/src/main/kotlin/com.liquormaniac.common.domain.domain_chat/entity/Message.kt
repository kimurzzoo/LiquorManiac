package com.liquormaniac.common.domain.domain_chat.entity

import com.liquormaniac.common.domain.domain_core.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name="message")
class Message(chatroomId : Long, userId : Long, isImage : Boolean = false) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id : Long? = null

    @Column(name = "chatroom_id", columnDefinition = "bigint", nullable = false)
    var chatroomId : Long = chatroomId

    @Column(name = "user_id", columnDefinition = "bigint", nullable = false)
    var userId : Long = userId

    @Column(name = "content", columnDefinition = "text")
    var content : String? = null

    @Column(name = "is_image", nullable = false)
    var isImage : Boolean = isImage

    @Column(name = "youtube_url", columnDefinition = "text")
    var youtubeUrl : String? = null

    @Column(name = "alcohol_id", columnDefinition = "bigint")
    var alcoholId : Long? = null
}