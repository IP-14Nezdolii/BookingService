package com.example.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @NonNull private String email;
    @NonNull private String password;
    
    private String name;
    private String surname;
   
    @Builder.Default
    private byte ownerScore = USER_NOT_SCORED;

    static public User makeNewUser(User userForm) {
        return User.builder()
        .email(userForm.getEmail())
        .password(userForm.getPassword())
        .name(userForm.getName())
        .surname(userForm.getSurname())
        .build();
    }

    @JsonIgnore
    static public final byte USER_NOT_SCORED = -1;
}
