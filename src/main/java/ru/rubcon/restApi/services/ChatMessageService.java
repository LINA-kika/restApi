package ru.rubcon.restApi.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rubcon.restApi.models.ChatMessage;
import ru.rubcon.restApi.models.MessageStatus;
import ru.rubcon.restApi.repos.ChatMessageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository repository;

    private final ChatRoomService chatRoomService;

    public ChatMessageService(ChatMessageRepository repository, ChatRoomService chatRoomService) {
        this.repository = repository;
        this.chatRoomService = chatRoomService;
    }

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setMessageStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(Long senderId, Long recipientId) {
        return repository.countBySenderIdAndRecipientIdAndMessageStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(Long senderId, Long recipientId) {
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);

        var messages =
                chatId.map(cId -> repository.findByChatIdOrderByTimestampDesc(cId)).orElse(new ArrayList<>());

        if (messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    public ChatMessage findById(Long id) {
        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setMessageStatus(MessageStatus.DELIVERED);
                    return repository.save(chatMessage);
                }).get();
    }

    public void updateStatuses(Long senderId, Long recipientId, MessageStatus status) {
        List<ChatMessage> saved = repository.findByRecipientIdAndSenderId(recipientId, senderId);
        for(ChatMessage item: saved){
            item.setMessageStatus(status);
            repository.save(item);
        }


    }

}
