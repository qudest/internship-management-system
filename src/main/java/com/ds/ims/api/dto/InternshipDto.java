package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternshipDto {
    Long id;
    @NonNull
    String title;
    String description;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty("start_date")
    LocalDateTime startDate;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty("recording_end_date")
    LocalDateTime recordingEndDate;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty("end_date")
    LocalDateTime endDate;
}
