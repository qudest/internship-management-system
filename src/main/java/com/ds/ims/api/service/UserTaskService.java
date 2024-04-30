package com.ds.ims.api.service;

import com.ds.ims.api.dto.UserTaskDto;
import com.ds.ims.api.mapper.UserTaskMapper;
import com.ds.ims.storage.entity.TaskEntity;
import com.ds.ims.storage.entity.UserEntity;
import com.ds.ims.storage.entity.UserTaskEntity;
import com.ds.ims.storage.repository.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserTaskService {
    UserTaskRepository userTaskRepository;
    TaskService taskService;
    UserService userService;

    public List<UserTaskDto> getTasks(Long internshipId, Long lessonId, Long authenticatedAccountId) {
        List<UserTaskEntity> userTasks = userTaskRepository.findAllByUserId(authenticatedAccountId);
        List<UserTaskDto> userTaskDtos = new ArrayList<>();

        for (UserTaskEntity userTask : userTasks) {
            TaskEntity task = userTask.getTask();
            if (task.getLesson().getId().equals(lessonId) && task.getLesson().getInternship().getId().equals(internshipId)) {
                userTaskDtos.add(UserTaskMapper.INSTANCE.toDto(task.getTitle(), userTask));
            }
        }

        return userTaskDtos;
    }

    public UserTaskDto getTask(Long internshipId, Long lessonId, Long taskId, Long authenticatedAccountId) {
        TaskEntity task = taskService.findById(taskId).orElseThrow(() -> new NullPointerException("Task not found with id: " + taskId));

        if (!task.getLesson().getId().equals(lessonId) || !task.getLesson().getInternship().getId().equals(internshipId)) {
            throw new BadRequestException("Task does not belong to the specified lesson and internship");
        }

        UserEntity user = userService.findByAccountId(authenticatedAccountId).orElseThrow(() -> new NullPointerException("User not found with account id: " + authenticatedAccountId));

        UserTaskEntity userTaskEntity = findByUserIdAndTaskId(user.getId(), taskId);
        return UserTaskMapper.INSTANCE.toDto(task.getTitle(), userTaskEntity);
    }

    public List<UserTaskEntity> findAllByUserId(Long userId) {
        return userTaskRepository.findAllByUserId(userId);
    }

    public UserTaskEntity findByUserIdAndTaskId(Long userId, Long taskId) {
        return userTaskRepository.findByUserIdAndTaskId(userId, taskId);
    }
}
