package com.ds.ims.storage.entity;

import com.ds.ims.storage.entity.status.UserTaskStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

/**
 * Сущность для задания пользователя
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_task")
public class UserTaskEntity {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_task_seq")
    @SequenceGenerator(name = "user_task_seq", sequenceName = "user_task_seq", allocationSize = 1)
    Long id;

    /**
     * Пользователь
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    UserEntity user;

    /**
     * Задание
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    TaskEntity task;

    /**
     * Forked URL репозитория в GitLab
     */
    @Column(name = "forked_gitlab_repository_url", nullable = false)
    String forkedGitlabRepositoryUrl;

    /**
     * Статус задания
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    UserTaskStatus status;

    /**
     * Дата последней проверки
     */
    @Column(name = "last_check_date", nullable = false)
    Date lastCheckDate;
}
