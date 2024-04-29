package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.RequestDto;
import com.ds.ims.storage.entity.InternshipRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = UserMapper.class)
public interface RequestMapper {
    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);
    RequestDto toDto(InternshipRequestEntity requestEntity);
    List<RequestDto> toDtos(List<InternshipRequestEntity> requestEntities);
}
