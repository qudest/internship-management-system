package com.ds.ims.api.dto;

import com.ds.ims.storage.entity.status.InternshipStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternshipDto {
    Long id;
    String title;
    String description;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonProperty("start_date")
    LocalDateTime startDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonProperty("recording_end_date")
    LocalDateTime recordingEndDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonProperty("end_date")
    LocalDateTime endDate;
    InternshipStatus status;
}
