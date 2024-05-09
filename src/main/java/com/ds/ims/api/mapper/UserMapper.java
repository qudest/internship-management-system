package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.UserDto;
import com.ds.ims.storage.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Маппер для пользователя
 */
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(UserEntity userEntity);

    UserEntity toEntity(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "email", ignore = true)
    void updateEntityFromDto(UserDto dto, @MappingTarget UserEntity entity);
}
