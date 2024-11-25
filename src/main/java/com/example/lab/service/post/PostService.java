package com.example.lab.service.post;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.lab.entity.post.Post;
import com.example.lab.repository.interfaces.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostCreator postCreator;

    public List<Post> readAll() {
        return postRepository.getAll();
    }

    public Optional<Post> readPost(long id) {
        return postRepository.getById(id);
    }

    public Optional<Post> createPost(Post post) {
        return postCreator.createPost(post);
    }

    public boolean setAvaliablePost(long id, boolean avaliable) {
        return postRepository.setAvailable(avaliable, id);
    }

    public List<Post> getPostList(String userId) {
        return postRepository.getPostList(userId);
    }

    public List<Post> getWishList(String userId) {
        return postRepository.getWishList(userId);
    }

    ///////////////////////////////////////////////////////////////////////

    public Optional<Post> updatePost(Post post) {
        return postRepository.update(post);
    }

    public boolean deletePost(long id) {
        return postRepository.deleteById(id);
    }
}
