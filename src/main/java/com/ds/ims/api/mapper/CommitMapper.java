package com.ds.ims.api.mapper;

import com.ds.ims.api.dto.CommitDto;
import com.ds.ims.storage.entity.UserTaskEntity;
import org.gitlab4j.api.models.Commit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Маппер для коммита
 */
@Mapper
public interface CommitMapper {
    CommitMapper INSTANCE = Mappers.getMapper(CommitMapper.class);
    @Mappings(
            {
                    @Mapping(source = "userTaskEntity.id", target = "userTaskId"),
                    @Mapping(source = "commit.title", target = "title"),
                    @Mapping(source = "userTaskEntity.user.id", target = "userId"),
                    @Mapping(source = "userTaskEntity.user.account.username", target = "username"),
                    @Mapping(source = "commit.createdAt", target = "commitDate"),
                    @Mapping(source = "commit.committerName", target = "lastAuthor"),
                    @Mapping(source = "commit.webUrl", target = "webUrl")


            }
    )
    CommitDto toDto(UserTaskEntity userTaskEntity, Commit commit);
}
