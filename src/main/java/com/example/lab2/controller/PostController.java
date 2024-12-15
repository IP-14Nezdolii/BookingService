package com.example.lab2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.lab2.controller.specifications.PostFilter;
import com.example.lab2.entity.post.Post;
import com.example.lab2.entity.post.Post.PostStatus;
import com.example.lab2.security.AuthenticationHandler;
import com.example.lab2.security.UserDetailsImpl;
import com.example.lab2.service.post.PostService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts/page")
    public ResponseEntity<List<Post>> getPage(
        @RequestParam(name = "page-number") int number,
        @RequestParam(name = "page-size") int size,
        @RequestBody PostFilter filter
    ) {      
        final List<Post> posts = postService.getPage(filter, filter.getPageable(number, size));

        return !posts.isEmpty()
           ? new ResponseEntity<>(posts, HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("")
    public ResponseEntity<Post> getPost(@RequestParam long id) {
        final Optional<Post> result = postService.findPost(id);

        if(result.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        Post post = result.get();

        if (post.getPostStatus() == PostStatus.AVAILABLE)
            return new ResponseEntity<>(post, HttpStatus.OK);

        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
            authentication.isAuthenticated() &&
            //when Anonymous Authentication is enabled
            authentication instanceof AnonymousAuthenticationToken 
        )
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (post.getPostStatus() == PostStatus.UNAVAILABLE)
            if (AuthenticationHandler.mayUser(authentication)
                    .hasUserRole()
                    .isOwner(post.getOwnerId())
                    .or()
                    .hasAdminRole()
                    .handle()
            ) return new ResponseEntity<>(post, HttpStatus.OK);
        

        if (post.getPostStatus() == PostStatus.ON_DELETE)
            if (AuthenticationHandler.mayUser(authentication)
                    .hasAdminRole()
                    .handle()
            ) return new ResponseEntity<>(post, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Post>> getPostList() {
        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();

        Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

        final List<Post> postList = postService.getPostList(userId);

        return !postList.isEmpty()
           ? new ResponseEntity<>(postList, HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(
        @RequestPart("data") Post postForm, 
        @RequestPart("file") @NotNull MultipartFile photo
    ) {
        if (photo.isEmpty())    
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String contentType = photo.getContentType();
        if (!("image/jpeg".equals(contentType) || "image/jpg".equals(contentType))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();

        Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        postForm.getHousing().setOwnerId(userId);

        return postService.createPost(postForm, photo)
            .map(post -> new ResponseEntity<>(post, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping("/update")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) {
        final Optional<Post> result = postService.findPost(post.getId());

        if (result.isEmpty()) 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Post oldPost = result.get();

        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();
        
        if (!AuthenticationHandler.mayUser(authentication)
                .hasUserRole()
                .isOwner(oldPost.getOwnerId())
                .handle()
        ) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        post.getHousing().setOwnerId(oldPost.getOwnerId());
        
        return postService.updatePost(post)
            .map(updatedPost -> new ResponseEntity<>(updatedPost, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Post> deletePost(@RequestParam Long id) {
        final Optional<Post> result = postService.findPost(id);

        if (result.isEmpty()) 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Post post = result.get();

        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();
        
        if (!AuthenticationHandler.mayUser(authentication)
                .hasUserRole()
                .isOwner(post.getOwnerId())
                .or()
                .hasAdminRole()
                .handle()
        ) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        return postService.deletePost(id)
           ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
           : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/photo")
    public ResponseEntity<Resource> downloadPhoto(@RequestParam("post-id") Long postId) {
        final Optional<Post> result = postService.findPost(postId);

        if (result.isEmpty()) 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Post post = result.get();

        return  postService.getPostPhoto(post.getImageKey())
           .map(postPhoto -> ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(postPhoto))
           .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
