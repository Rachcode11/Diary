package com.diary.diary.data.model;

import com.diary.diary.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String emailAddress;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private UserProfile userProfile;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "diary_id")
    private List<Diary> diaryList = new ArrayList<>();

    public void setUsername(String username){
        this.username = "@"+username;
    }

    public void addDiary(Diary diary){
        diaryList.add(diary);
    }

    public void removeDiary(Diary diary){
        diaryList.remove(diary);
    }

}
