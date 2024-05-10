package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.InternshipDto;
import com.ds.ims.storage.entity.InternshipEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Маппер для стажировки
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface InternshipMapper {
    InternshipMapper INSTANCE = Mappers.getMapper(InternshipMapper.class);

    InternshipDto toDto(InternshipEntity internshipEntity);

    List<InternshipDto> toDtos(List<InternshipEntity> internshipEntities);

    @Mapping(target = "id", ignore = true)
    InternshipEntity toEntity(InternshipDto internshipDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(InternshipDto internshipDto, @MappingTarget InternshipEntity existingInternship);
}
