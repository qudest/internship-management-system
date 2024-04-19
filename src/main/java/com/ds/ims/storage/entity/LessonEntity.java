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
@Table(name = "lesson")
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_seq")
    @SequenceGenerator(name = "lesson_seq", sequenceName = "lesson_seq", allocationSize = 1)
    Long id;

    String title;

    String description;

    @ManyToOne
    @JoinColumn(name = "internship_id")
    InternshipEntity internship;
}
