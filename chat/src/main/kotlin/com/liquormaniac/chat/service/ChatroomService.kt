package com.liquormaniac.chat.service

import com.liquormaniac.common.core.core_web.dto.ResponseDTO
import com.liquormaniac.common.core.core_web.enums.ResponseCode
import com.liquormaniac.common.core.core_web.exception.ResponseException
import com.liquormaniac.common.domain.domain_chat.entity.Chatroom
import com.liquormaniac.common.domain.domain_chat.repository.ChatroomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class ChatroomService(private val chatroomRepository: ChatroomRepository) {

    @Transactional(rollbackFor = [Exception::class])
    fun createChatroom(userId : Long, otherUserId : Long) : ResponseDTO<Long>
    {
        try {
            if(userId == otherUserId)
            {
                return ResponseDTO(ResponseCode.CREATECHATROOM_NO_SELF_CHAT)
            }

            val prevChatroomList = chatroomRepository.findByUserId1AndUserId2(userId, otherUserId)

            if(prevChatroomList.isEmpty())
            {
                val chatroom = Chatroom(userId, otherUserId)
                chatroomRepository.save(chatroom)
                return ResponseDTO(ResponseCode.SUCCESS, chatroom.id)
            }

            if(prevChatroomList.size > 1)
            {
                return ResponseDTO(ResponseCode.CREATECHATROOM_MANY_SAME_CHATROOMS)
            }

            val prevChatroom = prevChatroomList[0]

            if(prevChatroom.userId1 == userId)
            {
                if(prevChatroom.isUser1)
                {
                    return ResponseDTO(ResponseCode.CREATECHATROOM_ALREADY_EXISTS)
                }
                else
                {
                    if(prevChatroom.isUser2)
                    {
                        prevChatroom.isUser1 = true
                        chatroomRepository.save(prevChatroom)
                        return ResponseDTO(ResponseCode.SUCCESS, prevChatroom.id)
                    }
                    else
                    {
                        return ResponseDTO(ResponseCode.CREATECHATROOM_NOT_DELETED_YET)
                    }
                }
            }
            else if(prevChatroom.userId2 == userId)
            {
                if(prevChatroom.isUser2)
                {
                    return ResponseDTO(ResponseCode.CREATECHATROOM_ALREADY_EXISTS)
                }
                else
                {
                    if(prevChatroom.isUser1)
                    {
                        prevChatroom.isUser2 = true
                        chatroomRepository.save(prevChatroom)
                        return ResponseDTO(ResponseCode.SUCCESS, prevChatroom.id)
                    }
                    else
                    {
                        return ResponseDTO(ResponseCode.CREATECHATROOM_NOT_DELETED_YET)
                    }
                }
            }
            else
            {
                return ResponseDTO(ResponseCode.CREATECHATROOM_WRONG_CHATROOM)
            }
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            throw ResponseException(ResponseCode.SERVER_ERROR, e)
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun leaveChatroom(userId : Long, chatroomId : Long) : ResponseDTO<Unit>
    {
        try {
            val chatroom = chatroomRepository.findById(chatroomId).orElse(null)
                ?: return ResponseDTO(ResponseCode.LEAVECHATROOM_NO_CHATROOM)

            if(chatroom.userId1 != userId && chatroom.userId2 != userId)
            {
                return ResponseDTO(ResponseCode.LEAVECHATROOM_NOT_YOURS)
            }

            if(chatroom.userId1 == userId)
            {
                if(!chatroom.isUser1)
                {
                    return ResponseDTO(ResponseCode.LEAVECHATROOM_ALREADY_LEAVED)
                }
                else
                {
                    chatroom.isUser1 = false
                }
            }
            else
            {
                if(!chatroom.isUser2)
                {
                    return ResponseDTO(ResponseCode.LEAVECHATROOM_ALREADY_LEAVED)
                }
                else
                {
                    chatroom.isUser2 = false
                }
            }

            if(!chatroom.isUser1 && !chatroom.isUser2)
            {
                chatroomRepository.delete(chatroom)
            }
            else
            {
                chatroomRepository.save(chatroom)
            }

            return ResponseDTO(ResponseCode.SUCCESS)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
            throw ResponseException(ResponseCode.SERVER_ERROR, e)
        }
    }
}