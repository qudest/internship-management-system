package com.ds.ims.api.service;

import com.ds.ims.api.dto.MessageDto;
import com.ds.ims.api.mapper.MessageMapper;
import com.ds.ims.storage.entity.AccountEntity;
import com.ds.ims.storage.entity.MessageEntity;
import com.ds.ims.storage.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с сообщениями
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class MessageService {
    MessageRepository messageRepository;

    /**
     * Получить все сообщения по id получателя
     *
     * @param receiverId - id получателя
     * @return список сообщений (entities)
     */
    public List<MessageEntity> findAllByReceiverId(Long receiverId) {
        return messageRepository.findAllByReceiverId(receiverId);
    }

    /**
     * Получить все сообщения по id получателя
     *
     * @param receiverId - id получателя
     * @return список сообщений (dtos)
     */
    public List<MessageDto> getMessages(Long receiverId) {
        return MessageMapper.INSTANCE.toDtos(findAllByReceiverId(receiverId));
    }

    /**
     * Отправить сообщение
     *
     * @param receiver - получатель
     * @param messageDto - сообщение
     */
    public void sendMessage(AccountEntity receiver, MessageDto messageDto) {
        MessageEntity messageEntity = MessageMapper.INSTANCE.toEntity(messageDto);
        messageEntity.setReceiver(receiver);
        messageRepository.save(messageEntity);
    }
}
