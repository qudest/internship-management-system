package com.ds.ims.storage.entity;

import com.ds.ims.storage.entity.status.EducationalStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    Long id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "middle_name")
    String middleName;

    @Column(name = "last_name")
    String lastName;

    String email;

    String mobile;

    @Column(name = "telegram_id")
    String telegramId;

    @Column(name = "personal_information")
    String personalInformation;

    @Column(name = "birth_date")
    LocalDate birthDate;

    String city;

    @Column(name = "educational_status")
    @Enumerated(EnumType.STRING)
    EducationalStatus educationalStatus;

    String university;

    String faculty;

    String speciality;

    Integer course;
}
