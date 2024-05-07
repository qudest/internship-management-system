package com.ds.ims.storage.entity;

import com.ds.ims.storage.entity.status.InternshipStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Сущность для стажировки
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "internship")
public class InternshipEntity {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internship_seq")
    @SequenceGenerator(name = "internship_seq", sequenceName = "internship_seq", allocationSize = 1)
    Long id;

    /**
     * Название
     */
    @Column(nullable = false)
    String title;

    /**
     * Описание
     */
    String description;

    /**
     * Дата начала
     */
    @Column(name = "start_date", nullable = false)
    LocalDateTime startDate;

    /**
     * Дата окончания
     */
    @Column(name = "end_date")
    LocalDateTime endDate;

    /**
     * Дата окончания записи
     */
    @Column(name = "recording_end_date")
    LocalDateTime recordingEndDate;

    /**
     * Статус
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    InternshipStatus status;
}
