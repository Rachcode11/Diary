package com.diary.diary.services;

import com.diary.diary.data.model.UserProfile;

import java.util.List;

public interface UserProfileService {

    UserProfile findUserProfile(Long id);
    List<UserProfile> getAllUserProfile();
    UserProfile updateUserProfile(Long id, UserProfile userProfile);
    void deleteProfile(Long id);
}
