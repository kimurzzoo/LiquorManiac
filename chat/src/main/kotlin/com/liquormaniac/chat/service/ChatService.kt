package com.liquormaniac.chat.service

import com.liquormaniac.common.core.core_web.dto.ResponseDTO
import com.liquormaniac.common.core.core_web.enums.ResponseCode
import com.liquormaniac.common.core.core_web.exception.ResponseException
import com.liquormaniac.common.domain.domain_chat.entity.Message
import com.liquormaniac.common.domain.domain_chat.repository.ChatroomRepository
import com.liquormaniac.common.domain.domain_chat.repository.MessageImageRepository
import com.liquormaniac.common.domain.domain_chat.repository.MessageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ChatService(private val messageRepository: MessageRepository,
                  private val chatroomRepository: ChatroomRepository,
                  private val messageImageRepository: MessageImageRepository) {

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun messages(userId: Long, chatroomId: Long) : ResponseDTO<List<Message>>
    {
        try {
            val chatroom = chatroomRepository.findById(chatroomId).orElse(null)
                ?: return ResponseDTO(ResponseCode.MESSAGES_NO_CHATROOM)

            if(chatroom.userId1 != userId && chatroom.userId2 != userId)
            {
                return ResponseDTO(ResponseCode.MESSAGES_NOT_YOURS)
            }

            val messages = messageRepository.findByChatroomId(chatroomId)
            return ResponseDTO(ResponseCode.SUCCESS, messages)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            throw ResponseException(ResponseCode.SERVER_ERROR, e)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun uploadMessageImages(userId : Long, chatroomId: Long, imageFiles : List<MultipartFile>) : ResponseDTO<Unit>
    {
        try {
            val chatroom = chatroomRepository.findById(chatroomId).orElse(null)
                ?: return ResponseDTO(ResponseCode.UPLOADMESSAGEIMAGES_NO_CHATROOM)

            if(chatroom.userId1 != userId && chatroom.userId2 != userId)
            {
                return ResponseDTO(ResponseCode.UPLOADMESSAGEIMAGES_NOT_YOUR_CHATROOM)
            }

            /* TODO 이미지 파일들 이름 재지정
                AWS S3 버킷의 chatroomid/messageid 경로에 저장
                MessageImage랑 Message 저장
                kafka로 다른 인스턴스에 Message 이벤트 배포
             */
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            throw ResponseException(ResponseCode.SERVER_ERROR, e)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun sendMessage(userId: Long, chatroomId: Long, content: String) : ResponseDTO<Unit>
    {
        try {
            val chatroom = chatroomRepository.findById(chatroomId).orElse(null)
                ?: return ResponseDTO(ResponseCode.SENDMESSAGE_NO_CHATROOM)

            if(chatroom.userId1 != userId && chatroom.userId2 != userId)
            {
                return ResponseDTO(ResponseCode.SENDMESSAGE_NOT_YOUR_CHATROOM)
            }

            /* TODO Message 저장
                kafka로 다른 인스턴스에 Message 이벤트 배포
             */
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            throw ResponseException(ResponseCode.SERVER_ERROR, e)
        }
    }
}