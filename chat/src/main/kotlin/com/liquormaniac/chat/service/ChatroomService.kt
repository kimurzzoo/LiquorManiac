package com.liquormaniac.chat.service

import com.liquormaniac.common.domain.domain_chat.repository.ChatroomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class ChatroomService(private val chatroomRepository: ChatroomRepository) {

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun createChatroom(userId : Long, otherUserId : Long) : Long
    {
        try {

        }
    }
}