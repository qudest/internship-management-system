package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.UserTaskDto;
import com.ds.ims.storage.entity.UserTaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = TaskMapper.class)
public interface UserTaskMapper {
    UserTaskMapper INSTANCE = Mappers.getMapper(UserTaskMapper.class);
    UserTaskDto toDto(UserTaskEntity userTaskEntity);
    List<UserTaskDto> toDtos(List<UserTaskEntity> userTaskEntities);
}
