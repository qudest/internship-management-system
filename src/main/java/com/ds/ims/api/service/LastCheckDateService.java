package com.ds.ims.api.service;

import com.ds.ims.storage.entity.UserTaskEntity;
import com.ds.ims.storage.repository.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class LastCheckDateService {
    UserTaskRepository userTaskRepository;

    public Date getLastCheckDateByUrl(String url) {
        return userTaskRepository.findByForkedGitlabRepositoryUrl(url).getLastCheckDate();
    }

    public void updateLastCheckDateByUrl(String url, Date date) {
        UserTaskEntity userTaskEntity = userTaskRepository.findByForkedGitlabRepositoryUrl(url);
        userTaskEntity.setLastCheckDate(date);
        userTaskRepository.save(userTaskEntity);
    }
}
