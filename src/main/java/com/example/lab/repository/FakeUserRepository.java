package com.example.lab.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.lab.entity.User;
import com.example.lab.repository.interfaces.UserRepository;

@Repository
public class FakeUserRepository implements UserRepository {    
    private final static Map<String, User> USERS = new HashMap<>();
    private final static Map<String, String> PASSWORDS = new HashMap<>();

    @Override
    public List<User> getAll() {
        return new ArrayList<User>(USERS.values());
    }

    @Override
    public Optional<User> getById(String email) {
        return Optional.ofNullable(USERS.get(email));
    }

    @Override
    public Optional<User> save(User user) {
        if(USERS.containsKey(user.getEmail()) || 
            PASSWORDS.containsKey(user.getEmail())) {
            return Optional.ofNullable(null);
        }
        
        USERS.put(user.getEmail(), user);
        PASSWORDS.put(user.getEmail(), user.getPassword());
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> update(User user) {
        if(USERS.containsKey(user.getEmail()) &&
            !PASSWORDS.values().contains(user.getPassword())) {
                
            USERS.put(user.getEmail(), user);
            PASSWORDS.put(user.getEmail(), user.getPassword());
            return Optional.ofNullable(user);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public boolean deleteById(String email) {
        return  USERS.remove(email) != null && 
                PASSWORDS.remove(email) != null;
    }
}
