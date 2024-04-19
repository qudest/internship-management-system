package com.ds.ims.storage.entity;

import com.ds.ims.storage.entity.status.InternshipStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "internship")
public class InternshipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String title;

    String description;

    LocalDateTime startDate;

    LocalDateTime endDate;

    LocalDateTime recordingEndDate;

    @Enumerated(EnumType.STRING)
    InternshipStatus status;
}
