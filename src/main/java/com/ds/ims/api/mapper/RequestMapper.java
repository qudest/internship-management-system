package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.RequestDto;
import com.ds.ims.storage.entity.InternshipRequestEntity;
import com.ds.ims.storage.entity.status.InternshipRequestStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Маппер для заявки на стажировку
 */
@Mapper(uses = UserMapper.class)
public interface RequestMapper {
    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    RequestDto toDto(InternshipRequestEntity requestEntity);

    List<RequestDto> toDtos(List<InternshipRequestEntity> requestEntities);

    void updateEntityStatus(@MappingTarget InternshipRequestEntity requestEntity, InternshipRequestStatus status);
}
