package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {
    String text;
    @JsonProperty("sender_name")
    String senderName;
    @JsonProperty("created_at")
    LocalDateTime createdAt;
}
