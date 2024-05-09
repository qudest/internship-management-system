package com.ds.ims.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * DTO для сообщения
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {
    @ApiModelProperty("Текст сообщения")
    String text;
    @ApiModelProperty("Имя отправителя")
    @JsonProperty("sender_name")
    String senderName;
    @ApiModelProperty("Дата создания")
    @JsonProperty("created_at")
    LocalDateTime createdAt;

    public MessageDto() {
    }

    public MessageDto(String text, String senderName) {
        this.text = text;
        this.senderName = senderName;
        this.createdAt = LocalDateTime.now();
    }
}
