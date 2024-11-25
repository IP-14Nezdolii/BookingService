package com.example.lab.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lab.entity.User;
import com.example.lab.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getAll() {
        final List<User> users = userService.readAll();

        return !users.isEmpty()
           ? new ResponseEntity<>(users, HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<User> get(@PathVariable(name = "user-id") String userId) {
        final Optional<User> user = userService.readUser(userId);

        return user.isPresent()
           ? new ResponseEntity<>(user.get(), HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
 
    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User userForm) {
        final Optional<User> user = userService.createUser(userForm);
       
        return user.isPresent()
           ? new ResponseEntity<>(user.get(), HttpStatus.CREATED)
           : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        final Optional<User> updatedUser = userService.updateUser(user);
        
        return updatedUser.isPresent()
           ? new ResponseEntity<>(updatedUser.get(), HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> delete(@RequestBody String userId) {

        return userService.deleteUser(userId)
           ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
           : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
