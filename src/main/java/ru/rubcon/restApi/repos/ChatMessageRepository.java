package ru.rubcon.restApi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rubcon.restApi.models.ChatMessage;
import ru.rubcon.restApi.models.MessageStatus;


import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Long countBySenderIdAndRecipientIdAndMessageStatus(Long senderId, Long recipientId, MessageStatus status);
    List<ChatMessage> findByChatIdOrderByTimestampDesc(String chatId);
    List<ChatMessage> findByRecipientIdAndSenderId(Long recipientId, Long senderId);
}
