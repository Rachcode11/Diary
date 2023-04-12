package com.diary.diary.services;

import com.diary.diary.dto.request.RegistrationRequest;
import com.diary.diary.dto.response.ApiResponse;

public interface RegistrationService {

    ApiResponse register(RegistrationRequest registrationRequest);
}
