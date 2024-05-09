package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.LessonDto;
import com.ds.ims.storage.entity.LessonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Маппер для урока
 */
@Mapper
public interface LessonMapper {
    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    LessonDto toDto(LessonEntity lessonEntity);

    List<LessonDto> toDtos(List<LessonEntity> lessonEntities);

    @Mapping(target = "id", ignore = true)
    LessonEntity toEntity(LessonDto lessonDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(LessonDto lessonDto, @MappingTarget LessonEntity existingLesson);
}
