package com.example.lab.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.example.lab.entity.User;

public interface UserRepository {
    public List<User> getAll();

    public Optional<User> getById(String email);
    public Optional<User> save(User user);
    public Optional<User> update(User user);

    public boolean deleteById(String email);
}
