package com.ds.ims.storage.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Сущность для сообщения
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "message")
public class MessageEntity {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    @SequenceGenerator(name = "message_seq", sequenceName = "message_seq", allocationSize = 1)
    Long id;

    /**
     * Текст
     */
    @Column(nullable = false)
    String text;

    /**
     * Отправитель
     */
    @Column(name = "sender_name", nullable = false)
    String senderName;

    /**
     * Дата создания
     */
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    /**
     * Получатель(аккаунт)
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id")
    AccountEntity receiver;
}
