package com.ds.ims.api.service;

import com.ds.ims.api.dto.UserTaskDto;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserTaskService {
    //todo impl
    public List<UserTaskDto> getTasks(Long id, Long lessonId, Long authenticatedAccountId) {
        return null;
    }

    //todo impl
    public UserTaskDto getTask(Long internshipId, Long lessonId, Long taskId, Long authenticatedAccountId) {
        return null;
    }
}
