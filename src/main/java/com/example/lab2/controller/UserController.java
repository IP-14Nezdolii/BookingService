package com.example.lab2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lab2.entity.User;
import com.example.lab2.security.AuthenticationHandler;
import com.example.lab2.security.UserDetailsImpl;
import com.example.lab2.service.user.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
 
    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody User userForm) {
       
        return userService.createUser(userForm)
            .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @GetMapping("/log-in")
    public ResponseEntity<User> logIn() {
        var userDetails = (UserDetailsImpl)
            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        return userDetails != null
            ? new ResponseEntity<>(userDetails.getUser(), HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();
        
        var userDetails = ((UserDetailsImpl)authentication.getPrincipal());

        user.setUserId(userDetails.getId());
        
        return userService.updateUser(user)
            .map(updatedUser -> new ResponseEntity<>(updatedUser, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestParam long id) {
        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();

        if (!AuthenticationHandler.mayUser(authentication)
                .hasUserRole()
                .isOwner(id)
                .or()
                .hasAdminRole()
                .handle()
        ) 
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        return userService.deleteUser(id)
           ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
