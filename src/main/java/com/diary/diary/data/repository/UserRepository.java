package com.diary.diary.data.repository;

import com.diary.diary.Status;
import com.diary.diary.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    @Modifying
    @Query("UPDATE User user " +
            "SET user.status = ?1 " +
            "WHERE user.emailAddress = ?2")
    void verifyUser(Status verify, String email);
}
