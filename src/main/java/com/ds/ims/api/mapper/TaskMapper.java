package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.TaskDto;
import com.ds.ims.storage.entity.TaskEntity;
import org.gitlab4j.api.models.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Маппер для задачи
 */
@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "name")
    @Mapping(target = "gitlabRepositoryUrl", source = "webUrl")
    TaskEntity gitlabProjectToTaskEntity(Project project);

    TaskDto toDto(TaskEntity taskEntity);
}
