package com.example.lab2.service.user;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.example.lab2.entity.User;
import com.example.lab2.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCreator {
    private final UserRepository userRepository;

    private final String regexEmailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
                                      + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private final String regexPasswordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,14}$";

    private final Pattern emailPattern = Pattern.compile(regexEmailPattern);
    private final Pattern passwordPattern = Pattern.compile(regexPasswordPattern);


    private boolean emailIsValid(String email) {
        //username@domain.com
        //user.name@domain.com
        //user-name@domain.com
        //username@domain.co.in
        //user_name@domain.com
        return email != null && emailPattern.matcher(email).matches();
    }

    private boolean passwordIsValid(String password) {
        //"abc123333333333"
        //"a1b2c3444444444444"
        //"aas2c3555555555"
        return password != null && passwordPattern.matcher(password).matches();
    }

    public Optional<User> createUser(User userForm) {
        
        if (userForm.getCardCode() == null) 
            return  Optional.ofNullable(null); 

        //check password
        if (!passwordIsValid(userForm.getPassword())) 
            return  Optional.ofNullable(null); 
        if (userRepository.existsByPassword(userForm.getPassword())) 
            return  Optional.ofNullable(null);

        //check email
        if (!emailIsValid(userForm.getEmail()))
            return  Optional.ofNullable(null);
        if (userRepository.existsByEmail(userForm.getEmail()))
            return  Optional.ofNullable(null);

        return Optional.ofNullable(userRepository.save(newUser(userForm)));
    }
  
    public User newUser(User userForm) {
        return User.builder()
        .email(userForm.getEmail())
        .password(userForm.getPassword())
        .name(userForm.getName())
        .surname(userForm.getSurname())
        .cardCode(userForm.getCardCode())
        .build();
    }
}
