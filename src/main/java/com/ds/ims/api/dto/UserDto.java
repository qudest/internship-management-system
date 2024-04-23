package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    @NonNull
    @JsonProperty("last_name")
    String lastName;
    @NonNull
    @JsonProperty("first_name")
    String firstName;
    @NonNull
    @JsonProperty("middle_name")
    String middleName;
    @NonNull
    String email;
    @NonNull
    String phone;
    @NonNull
    @JsonProperty("telegram_id")
    String telegramId;
    @NonNull
    @JsonProperty("personal_information")
    String personalInformation;
    @NonNull
    @JsonProperty("birth_date")
    LocalDate birthDate;
    @NonNull
    String city;
    @NonNull
    @JsonProperty("educational_status")
    String educationalStatus;
    String university;
    String faculty;
    String speciality;
    Integer course;
}
