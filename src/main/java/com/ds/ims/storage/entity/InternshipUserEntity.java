package com.ds.ims.storage.entity;

import com.ds.ims.storage.entity.status.InternshipUserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Сущность для стажера
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "internship_user")
public class InternshipUserEntity {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internship_user_seq")
    @SequenceGenerator(name = "internship_user_seq", sequenceName = "internship_user_seq", allocationSize = 1)
    Long id;

    /**
     * Пользователь
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    UserEntity user;

    /**
     * Стажировка
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "internship_id")
    InternshipEntity internship;

    /**
     * Статус
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    InternshipUserStatus status;

    /**
     * Дата поступления
     */
    @Column(name = "entry_date", nullable = false)
    LocalDateTime entryDate;

    /**
     * Дата завершения
     */
    @Column(name = "completion_date")
    LocalDateTime completionDate;
}
