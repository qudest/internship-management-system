package com.ds.ims.api.service;

import com.ds.ims.storage.entity.UserTaskEntity;
import com.ds.ims.storage.repository.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Date;

/**
 * Сервис для работы с датой последней проверки
 */
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class LastCheckDateService {
    UserTaskRepository userTaskRepository;

    /**
     * Получить дату последней проверки по url
     *
     * @param url - url
     * @return дата
     */
    public Date getLastCheckDateByUrl(String url) {
        UserTaskEntity userTaskEntity = userTaskRepository.findByForkedGitlabRepositoryUrl(url)
                .orElseThrow(() -> new NotFoundException("User task with url " + url + " not found"));
        return userTaskEntity.getLastCheckDate();
    }

    /**
     * Обновить дату последней проверки по url
     *
     * @param url  - url
     * @param date - дата
     */
    public void updateLastCheckDateByUrl(String url, Date date) {
        UserTaskEntity userTaskEntity = userTaskRepository.findByForkedGitlabRepositoryUrl(url)
                .orElseThrow(() -> new NotFoundException("User task with url " + url + " not found"));
        userTaskEntity.setLastCheckDate(date);
        userTaskRepository.save(userTaskEntity);
    }
}
