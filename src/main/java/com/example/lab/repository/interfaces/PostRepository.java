package com.example.lab.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.example.lab.entity.post.Post;

public interface PostRepository {
    public List<Post> getAll();

    public Optional<Post> getById(long id);
    public Optional<Post> save(Post post);
    public Optional<Post> update(Post post);
    
    public boolean setAvailable(boolean isAvailable, long id);

    public boolean deleteById(long id);

    public List<Post> getPostList(String userId);
    public List<Post> getWishList(String userId);
    public boolean saveWishList(String userId, List<Long> wishListIds);
}
