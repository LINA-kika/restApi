package ru.rubcon.restApi.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@Getter@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chat_room")
public class ChatRoom extends BaseEntity{
    private String chatId;
    private Long senderId;
    private Long recipientId;

}
