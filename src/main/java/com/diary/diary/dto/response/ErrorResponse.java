package com.diary.diary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@Data
@Builder
public class ErrorResponse {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime timeStamp;
    private HttpStatus statusCode;
    private String path;
    private Boolean isSuccessful;
}
