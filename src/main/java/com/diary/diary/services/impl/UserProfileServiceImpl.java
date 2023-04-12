package com.diary.diary.services.impl;

import com.diary.diary.data.model.UserProfile;
import com.diary.diary.data.repository.UserProfileRepository;
import com.diary.diary.exception.NotFoundException;
import com.diary.diary.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;
    @Override
    public UserProfile findUserProfile(Long id) {
        return foundProfileWithThis(id);
    }

    private UserProfile foundProfileWithThis(Long id) {
        Optional<UserProfile> foundProfile = userProfileRepository.findById(id);
        if(foundProfile.isPresent()) {
            return foundProfile.get();
        } else {
            throw new NotFoundException("User profile with id {" + id + "} not found");
        }
    }

    @Override
    public List<UserProfile> getAllUserProfile() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile updateUserProfile(Long id, UserProfile profile) {
        UserProfile foundProfile = foundProfileWithThis(id);
        foundProfile.setFirstName(profile.getFirstName());
        foundProfile.setLastName(profile.getLastName());
        foundProfile.setPhoneNumber(profile.getPhoneNumber());
        foundProfile.setTwitter(profile.getTwitter());
        return userProfileRepository.save(foundProfile);
    }

    @Override
    public void deleteProfile(Long id) {
        UserProfile foundProfile = foundProfileWithThis(id);
        userProfileRepository.delete(foundProfile);

    }
}
