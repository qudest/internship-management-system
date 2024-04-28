package com.ds.ims.storage.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    Long id;
    @Column(unique = true, nullable = false)
    String title;

    @Column(name = "gitlab_repository_url")
    String gitlabRepositoryUrl;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    LessonEntity lesson;
}
