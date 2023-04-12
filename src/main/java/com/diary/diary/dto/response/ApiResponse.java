package com.diary.diary.dto.response;

import lombok.*;

import java.time.ZonedDateTime;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private ZonedDateTime timeStamp;
    private int statusCode;
    private String message;
}
