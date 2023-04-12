package com.diary.diary.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "This  Field cannot be blank")
    private String userName;
    @Email(message = "This field requires a valid email address")
    @NotBlank(message = "This  Field cannot be blank")
    private String emailAddress;
}
