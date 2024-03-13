package com.liquormaniac.common.domain.domain_chat.repository

import com.liquormaniac.common.domain.domain_chat.entity.Chatroom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ChatroomRepository : JpaRepository<Chatroom, Long> {

    @Query("SELECT c FROM Chatroom c WHERE (c.userId1 = :userId1 AND c.userId2 = :userId2) OR (c.userId1 = :userId2 AND c.userId2 = :userId1)")
    fun findByUserId1AndUserId2(userId1: Long, userId2: Long) : List<Chatroom>
}