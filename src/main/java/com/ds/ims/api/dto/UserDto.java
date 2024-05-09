package com.ds.ims.api.dto;

import com.ds.ims.storage.entity.status.EducationalStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("Фамилия")
    @JsonProperty("last_name")
    String lastName;
    @ApiModelProperty("Имя")
    @JsonProperty("first_name")
    String firstName;
    @ApiModelProperty("Отчество")
    @JsonProperty("middle_name")
    String middleName;
    @ApiModelProperty("Электронная почта")
    String email;
    @ApiModelProperty("Телефон")
    String phone;
    @ApiModelProperty("Идентификатор Telegram")
    @JsonProperty("telegram_id")
    String telegramId;
    @ApiModelProperty("Персональная информация")
    @JsonProperty("personal_information")
    String personalInformation;
    @ApiModelProperty("Дата рождения")
    @JsonProperty("birth_date")
    LocalDate birthDate;
    @ApiModelProperty("Город")
    String city;
    @ApiModelProperty("Образование")
    @JsonProperty("educational_status")
    EducationalStatus educationalStatus;
    @ApiModelProperty("Университет")
    String university;
    @ApiModelProperty("Факультет")
    String faculty;
    @ApiModelProperty("Специальность")
    String speciality;
    @ApiModelProperty("Курс")
    Integer course;
}
