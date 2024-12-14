package com.example.lab2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lab2.entity.post.Post;
import com.example.lab2.security.UserDetailsImpl;
import com.example.lab2.service.WishListService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;

    @GetMapping("")
    public ResponseEntity<List<Post>> getWishList() {
        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();

        final Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        final List<Post> list =  wishListService.getWishList(userId);

        return  !list.isEmpty()
           ? new ResponseEntity<>(list, HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 
    @PutMapping("/add")
    public ResponseEntity<List<Post>> addToList(
            @RequestParam(name = "post-id") long postId
    ) {
        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();

        final Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        final Optional<Long> result = wishListService.addToList(userId, postId);
       
        return result.isPresent()
           ? getWishList()
           : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/remove")
    public ResponseEntity<List<Post>> removeFromList( 
        @RequestParam(name = "post-id") long postId
    ) {
        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();

        final Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

        wishListService.removeFromList(userId, postId);

        final List<Post> list =  wishListService.getWishList(userId);

        return  !list.isEmpty()
           ? new ResponseEntity<>(list, HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Long> clear() {
        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();

        final Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();
    
        return wishListService.delete(userId)
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
