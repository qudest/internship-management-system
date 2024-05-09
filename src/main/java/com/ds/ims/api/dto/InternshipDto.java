package com.ds.ims.api.dto;

import com.ds.ims.storage.entity.status.InternshipStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternshipDto {
    @ApiModelProperty("Идентификатор стажировки")
    Long id;
    @ApiModelProperty("Наименование стажировки")
    String title;
    @ApiModelProperty("Описание стажировки")
    String description;
    @ApiModelProperty("Дата начала стажировки")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonProperty("start_date")
    LocalDateTime startDate;
    @ApiModelProperty("Дата окончания записи на стажировку")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonProperty("recording_end_date")
    LocalDateTime recordingEndDate;
    @ApiModelProperty("Дата окончания стажировки")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonProperty("end_date")
    LocalDateTime endDate;
    @ApiModelProperty("Статус стажировки")
    InternshipStatus status;
}
