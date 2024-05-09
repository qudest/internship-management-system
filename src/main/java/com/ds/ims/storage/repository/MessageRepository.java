package com.ds.ims.storage.repository;

import com.ds.ims.storage.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с сообщениями
 */
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findAllByReceiverId(Long receiverId);
}
