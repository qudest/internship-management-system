package com.ds.ims.storage.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Сущность для задания
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "task")
public class TaskEntity {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    Long id;

    /**
     * Название
     */
    @Column(unique = true, nullable = false)
    String title;

    /**
     * URL репозитория в GitLab
     */
    @Column(name = "gitlab_repository_url", nullable = false)
    String gitlabRepositoryUrl;

    /**
     * Урок к которому относится задание
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "lesson_id")
    LessonEntity lesson;
}
