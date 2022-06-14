package ru.rubcon.restApi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rubcon.restApi.models.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
}
