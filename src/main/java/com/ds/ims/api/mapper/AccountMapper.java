package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.AccountDto;
import com.ds.ims.storage.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Маппер для аккаунта
 */
@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto toDto(AccountEntity accountEntity);
}
