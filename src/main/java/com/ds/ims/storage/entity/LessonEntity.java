package com.ds.ims.storage.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Сущность для урока
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "lesson")
public class LessonEntity {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_seq")
    @SequenceGenerator(name = "lesson_seq", sequenceName = "lesson_seq", allocationSize = 1)
    Long id;

    /**
     * Название
     */
    @Column(nullable = false)
    String title;

    /**
     * Содержание
     */
    @Column(nullable = false)
    String content;

    /**
     * Стажировка к которой относится урок
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "internship_id")
    InternshipEntity internship;
}
