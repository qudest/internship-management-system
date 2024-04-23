package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.InternshipDto;
import com.ds.ims.storage.entity.InternshipEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InternshipMapper {
    InternshipMapper INSTANCE = Mappers.getMapper(InternshipMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "endDate", target = "endDate"),
            @Mapping(source = "recordingEndDate", target = "recordingEndDate")
    })
    InternshipDto toDto(InternshipEntity internshipEntity);

    List<InternshipDto> toDtos(List<InternshipEntity> internshipEntities);
}
