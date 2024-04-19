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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internship_seq")
    @SequenceGenerator(name = "internship_seq", sequenceName = "internship_seq", allocationSize = 1)
    Long id;

    String title;

    String description;

    @Column(name = "start_date")
    LocalDateTime startDate;

    @Column(name = "end_date")
    LocalDateTime endDate;

    @Column(name = "recording_start_date")
    LocalDateTime recordingEndDate;

    @Enumerated(EnumType.STRING)
    InternshipStatus status;
}
