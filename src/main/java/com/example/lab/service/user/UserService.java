package com.example.lab.service.user;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.example.lab.entity.User;
import com.example.lab.repository.interfaces.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserCreator userCreator;

    public Optional<User> createUser(User userForm) {
        return userCreator.createUser(userForm);
    }

    public Optional<User> updateUser(User user) {
        Optional<User> getResult = userRepository.getById(user.getEmail());
        
        if(getResult.isPresent()){
            User oldUser = getResult.get();
            user.setOwnerScore(oldUser.getOwnerScore());
            return userRepository.update(user);
        }
        return Optional.ofNullable(null);
    }

    public boolean deleteUser(String email) {
        Optional<User> getResult = userRepository.getById(email);
        
        if(getResult.isPresent()){  
                return userRepository.deleteById(email);
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////
    public List<User> readAll() {
        return userRepository.getAll();
    }

    public Optional<User> readUser(String userId) {
        return userRepository.getById(userId);
    }
}   
