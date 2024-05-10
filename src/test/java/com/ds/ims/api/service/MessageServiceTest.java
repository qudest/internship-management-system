package com.ds.ims.api.service;

import com.ds.ims.api.dto.MessageDto;
import com.ds.ims.api.mapper.MessageMapper;
import com.ds.ims.storage.entity.AccountEntity;
import com.ds.ims.storage.entity.MessageEntity;
import com.ds.ims.storage.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MessageServiceTest {
    private MessageRepository messageRepository;
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageRepository = Mockito.mock(MessageRepository.class);
        messageService = new MessageService(messageRepository);
    }

    @Test
    void findAllByReceiverIdReturnsListOfMessages() {
        Long receiverId = 1L;
        when(messageRepository.findAllByReceiverId(receiverId)).thenReturn(Collections.singletonList(new MessageEntity()));

        List<MessageEntity> result = messageService.findAllByReceiverId(receiverId);
        assertFalse(result.isEmpty());
    }

    @Test
    void getMessagesReturnsListOfMessageDtos() {
        Long receiverId = 1L;
        when(messageRepository.findAllByReceiverId(receiverId)).thenReturn(Collections.singletonList(new MessageEntity()));

        List<MessageDto> result = messageService.getMessages(receiverId);
        assertFalse(result.isEmpty());
    }
}