package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.UserTaskDto;
import com.ds.ims.storage.entity.UserTaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserTaskMapper {
    UserTaskMapper INSTANCE = Mappers.getMapper(UserTaskMapper.class);
    UserTaskDto toDto(String title, UserTaskEntity userTaskEntity);
}
