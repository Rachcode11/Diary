package com.diary.diary.services.impl;

import com.diary.diary.Status;
import com.diary.diary.data.model.Diary;
import com.diary.diary.data.model.Token;
import com.diary.diary.data.model.User;
import com.diary.diary.data.model.UserProfile;
import com.diary.diary.data.repository.TokenRepository;
import com.diary.diary.data.repository.UserRepository;
import com.diary.diary.dto.request.ResendTokenRequest;
import com.diary.diary.dto.request.TokenRequest;
import com.diary.diary.dto.response.ApiData;
import com.diary.diary.exception.NotFoundException;
import com.diary.diary.services.EmailService;
import com.diary.diary.services.UserService;
import com.diary.diary.utils.TokenGenerator;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.diary.diary.utils.EmailUtils.buildEmail;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);

    }

    @Override
    public ApiData createAccount(TokenRequest tokenRequest) {
        tokenVerification(tokenRequest);
        userRepository.verifyUser(Status.VERIFIED, tokenRequest.getEmail());
        var foundUser = findByEmailIgnoreCase(tokenRequest.getEmail())
                .orElseThrow(()-> new NotFoundException("User Not Found"));
        return ApiData.builder()
                .data(foundUser.getUsername() + "User Verified Successfully")
                .build();
    }

    @Override
    public ApiData tokenVerification(TokenRequest tokenRequest) {
        var foundUser = findByEmailIgnoreCase(tokenRequest.getEmail())
                .orElseThrow(()-> new NotFoundException("User Does Not Exist"));
        Token foundToken = tokenRepository.findByToken(tokenRequest.getToken())
                .orElseThrow(()-> new NotFoundException("Token doesn't exist"));
        if (!Objects.equals(tokenRequest.getToken(), foundToken.getToken()))throw new NotFoundException("OTP isn't correct");
        if (foundToken.getExpiredTime().isBefore(LocalDateTime.now()))throw new NotFoundException("Otp Already expired");
        if (!Objects.equals(foundToken.getUser(), foundUser))throw new NotFoundException("Invalid Token ");
        tokenRepository.setConfirmedAt(LocalDateTime.now(), foundToken.getToken());
        tokenRepository.delete(foundToken);
        return ApiData.builder()
                .data("Token Verified")
                .build();
    }

    @Override
    public Optional<User> findByEmailIgnoreCase(String email) {
        return Optional.empty();
    }

    @Override
    public ApiData sendOTP(ResendTokenRequest tokenRequest) {
        var foundUser = userRepository.findByEmailIgnoreCase(tokenRequest.getEmailAddress())
                .orElseThrow(()-> new NotFoundException("User with " + tokenRequest.getEmailAddress() + " not found"));
        return generateToken(tokenRequest, foundUser);
    }

    private ApiData generateToken(ResendTokenRequest tokenRequest, User savedUser) {
        final String generateToken = TokenGenerator.generaToken();
        var token = new Token(generateToken, savedUser);
        if (tokenRepository.findByUserId(savedUser.getUsername()).isEmpty()) tokenRepository.save(token);
        else {
            var foundUserOTP = tokenRepository.findByUserId(savedUser.getEmailAddress()).get();
            foundUserOTP.setToken(generateToken);
            foundUserOTP.setCreatedTime(LocalDateTime.now().plusMinutes(10));
            foundUserOTP.setUser(savedUser);
            tokenRepository.save(foundUserOTP);
        }
        emailService.sendEmail(tokenRequest.getEmailAddress(), buildEmail(savedUser.getUsername(),generateToken));
        return ApiData.builder()
                .data("Token successfully sent to  " + tokenRequest.getEmailAddress())
                .build();
    }

    @Override
    public User updateEmailAddress(String emailAddress, Map<String, Object> emailAddressPatched) {
        Optional<User> foundUser = userRepository.findByEmailIgnoreCase(emailAddress);
        User user = null;
        if(foundUser.isPresent()){
            emailAddressPatched.forEach((key, value) ->{
                Field field = ReflectionUtils.findField(User.class, key);
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, foundUser.get(), value);
            });
        }else{
            throw new NotFoundException("User with id {" + emailAddress + "} not found ");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        User foundUser = findByEmailIgnoreCase(user.getEmailAddress()).orElseThrow(()-> new NotFoundException("User not found"));
        userRepository.delete(foundUser);
    }

    @Override
    public User assignProfile(String email ,UserProfile profile) {
        User user = findByEmailIgnoreCase(email).orElseThrow(()-> new NotFoundException("User not found"));
        user.setUserProfile(profile);
        return userRepository.save(user);
    }

    @Override
    public User addDiary(String email, Diary diary) {
        User foundUser = findByEmailIgnoreCase(email).orElseThrow(()-> new NotFoundException("User Not Found"));
        foundUser.addDiary(diary);
        return userRepository.save(foundUser);
    }

    @Override
    public User DeleteDiary(String email, Diary diary) {
        User foundUser = findByEmailIgnoreCase(email).orElseThrow(()-> new NotFoundException("User not found"));
        foundUser.removeDiary(diary);
        return userRepository.save(foundUser);
    }
}
