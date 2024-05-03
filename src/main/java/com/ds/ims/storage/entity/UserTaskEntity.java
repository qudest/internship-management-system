package com.ds.ims.storage.entity;

import com.ds.ims.storage.entity.status.UserTaskStatus;
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
@Table(name = "user_task")
public class UserTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_task_seq")
    @SequenceGenerator(name = "user_task_seq", sequenceName = "user_task_seq", allocationSize = 1)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    TaskEntity task;

    @Column(name = "forked_gitlab_repository_url")
    String forkedGitlabRepositoryUrl;

    @Enumerated(EnumType.STRING)
    UserTaskStatus status;
}
