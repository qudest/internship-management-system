package com.ds.ims.storage.entity;

import com.ds.ims.storage.entity.status.EducationalStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    Long id;

    String firstName;

    String middleName;

    String lastName;

    String email;

    String mobile;

    String telegramId;

    String personalInformation;

    LocalDate birthDate;

    String city;

    EducationalStatus educationalStatus;

    String university;

    String faculty;

    String speciality;

    Integer course;
}
