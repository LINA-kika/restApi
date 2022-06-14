package ru.rubcon.restApi.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chat_message")
public class ChatMessage extends BaseEntity{

    private String chatId;
    private Long senderId;
    private Long recipientId;
    private String senderName;
    private String recipientName;
    private String content;
    @CreationTimestamp
    private Date timestamp;
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

}
