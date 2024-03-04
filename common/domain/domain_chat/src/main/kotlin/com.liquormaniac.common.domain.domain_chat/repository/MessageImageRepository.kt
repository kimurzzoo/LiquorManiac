package com.liquormaniac.common.domain.domain_chat.repository

import com.liquormaniac.common.domain.domain_chat.entity.MessageImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageImageRepository : JpaRepository<MessageImage, Long> {
}