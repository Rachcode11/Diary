package com.diary.diary.services;

import com.diary.diary.data.model.Diary;
import com.diary.diary.data.model.User;
import com.diary.diary.data.model.UserProfile;
import com.diary.diary.dto.request.ResendTokenRequest;
import com.diary.diary.dto.request.TokenRequest;
import com.diary.diary.dto.response.ApiData;

import java.util.Map;
import java.util.Optional;

public interface UserService {

    void saveUser(User user);
    ApiData createAccount(TokenRequest tokenRequest);
    ApiData tokenVerification(TokenRequest tokenRequest);
    Optional<User> findByEmailIgnoreCase(String email);
    ApiData sendOTP(ResendTokenRequest tokenRequest);
    User updateEmailAddress(String emailAddress, Map<String, Object> emailAddressPatched);
    void deleteUser(User user);
    User assignProfile(String email, UserProfile profile);
    User addDiary(String email, Diary diary);
    User DeleteDiary(String email, Diary diary);
}
