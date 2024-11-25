package com.example.lab.service.user;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.example.lab.entity.User;
import com.example.lab.repository.interfaces.UserRepository;

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
        return emailPattern.matcher(email).matches();
    }

    private boolean passwordIsValid(String password) {
        //"abc123333333333"
        //"a1b2c3444444444444"
        //"aas2c3555555555"
        return passwordPattern.matcher(password).matches();
    }

    public Optional<User> createUser(User userForm) {
        if (emailIsValid(userForm.getEmail()) && 
            passwordIsValid(userForm.getPassword())) {

            return userRepository.save(User.makeNewUser(userForm));
        }      
        return  Optional.ofNullable(null); 
    }
}
