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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    TaskEntity task;

    String forkedGitLabRepositoryUrl;

    @Enumerated(EnumType.STRING)
    UserTaskStatus status;
}
