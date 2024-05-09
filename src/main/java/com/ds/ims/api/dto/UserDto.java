package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

/**
 * DTO для пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    @JsonProperty("last_name")
    String lastName;
    @JsonProperty("first_name")
    String firstName;
    @JsonProperty("middle_name")
    String middleName;
    String email;
    String phone;
    @JsonProperty("telegram_id")
    String telegramId;
    @JsonProperty("personal_information")
    String personalInformation;
    @JsonProperty("birth_date")
    LocalDate birthDate;
    String city;
    @JsonProperty("educational_status")
    String educationalStatus;
    String university;
    String faculty;
    String speciality;
    Integer course;
}
