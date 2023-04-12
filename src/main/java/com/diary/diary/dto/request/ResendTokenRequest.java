package com.diary.diary.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ResendTokenRequest {

    @NotBlank(message = "This field is required")
    private String emailAddress;
}
