package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.MessageDto;
import com.ds.ims.storage.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Маппер для сообщения
 */
@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageEntity toEntity(MessageDto messageDto);

    MessageDto toDto(MessageEntity messageEntity);

    List<MessageDto> toDtos(List<MessageEntity> messageEntities);
}
