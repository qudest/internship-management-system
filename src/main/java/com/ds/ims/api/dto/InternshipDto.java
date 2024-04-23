package com.ds.ims.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternshipDto {
    Long id;
    String title;
    String description;
    LocalDateTime startDate;
    LocalDateTime recordingEndDate;
    LocalDateTime endDate;
}
