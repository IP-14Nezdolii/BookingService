package com.example.lab.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.lab.entity.post.Post;
import com.example.lab.repository.interfaces.PostRepository;

@Repository
public class FakePostRepository implements PostRepository {
    private final static Map<Long, Post> POSTS = new HashMap<>();
    private final static Map<String, List<Long>> WISH_LISTS = new HashMap<>();
    private static long idCounter = 0;

    @Override
    public List<Post> getAll() {
        return new ArrayList<Post>(POSTS.values());
    }

    @Override
    public Optional<Post> getById(long id) {
        return Optional.ofNullable(POSTS.get(id));
    }

    @Override
    public Optional<Post> save(Post post) {
        post.setId(idCounter);
        if(POSTS.putIfAbsent(post.getId(), post) == null) {
            idCounter++;
            return Optional.ofNullable(post);
        }   
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<Post> update(Post post) {
        if(POSTS.containsKey(post.getId())) {
            POSTS.put(post.getId(), post);
            return Optional.ofNullable(post);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public boolean setAvailable(boolean isAvailable, long id) {
        POSTS.get(id).setAvailable(isAvailable);
        return true;
    }

    @Override
    public boolean deleteById(long id) {
        return POSTS.remove(id) != null;
    }

    @Override
    public List<Post> getWishList(String userId) {
        List<Long> keys = WISH_LISTS.get(userId);
        List<Post> posts = new ArrayList<>();

        for (var key : keys) {
            posts.add(POSTS.get(key));
        }
        return posts;
    }

    @Override
    public boolean saveWishList(String userId, List<Long> wishListIds) {
        WISH_LISTS.put(userId, wishListIds);
        return true;
    }

    @Override
    public List<Post> getPostList(String userId) {
        var values = POSTS.values();
        List<Post> posts = new ArrayList<>();

        for (var post : values) {
            if (post.getHousing().getOwnerId().equals(userId)) {
                posts.add(post);
            }
        }
        return posts;
    }
}
