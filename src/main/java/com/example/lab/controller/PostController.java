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

import com.example.lab.entity.post.Post;
import com.example.lab.service.post.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<List<Post>> getAll() {
        final List<Post> posts = postService.readAll();

        return !posts.isEmpty()
           ? new ResponseEntity<>(posts, HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{post-id}")
    public ResponseEntity<Post> get(@PathVariable(name = "post-id") long postId) {
        final Optional<Post> post = postService.readPost(postId);

        return post.isPresent()
           ? new ResponseEntity<>(post.get(), HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Post> create(@RequestBody Post postForm) {
        final Optional<Post> post = postService.createPost(postForm);
       
        return post.isPresent()
           ? new ResponseEntity<>(post.get(), HttpStatus.CREATED)
           : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/update")
    public ResponseEntity<Post> update(@RequestBody Post user) {
        final Optional<Post> updatedUser = postService.updatePost(user);
        
        return updatedUser.isPresent()
           ? new ResponseEntity<>(updatedUser.get(), HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{post-id}")
    public ResponseEntity<Post> delete(@PathVariable(name = "post-id") long postId) {

        return postService.deletePost(postId)
           ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
           : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
