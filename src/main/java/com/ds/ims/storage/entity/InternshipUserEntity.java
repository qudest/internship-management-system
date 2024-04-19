package com.ds.ims.storage.entity;

import com.ds.ims.storage.entity.status.InternshipUserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "internship_user")
public class InternshipUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "internship_id")
    InternshipEntity internship;

    @Enumerated(EnumType.STRING)
    InternshipUserStatus status;

    LocalDateTime entryDate;

    LocalDate completionDate;
}
