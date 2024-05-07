package com.ds.ims.storage.entity;

import com.ds.ims.storage.entity.status.InternshipRequestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Сущность для запроса на стажировку
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "internship_request")
public class InternshipRequestEntity {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internship_request_seq")
    @SequenceGenerator(name = "internship_request_seq", sequenceName = "internship_request_seq", allocationSize = 1)
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
    InternshipRequestStatus status;
}
