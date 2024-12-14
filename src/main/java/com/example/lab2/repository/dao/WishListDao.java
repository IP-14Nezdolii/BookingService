package com.example.lab2.repository.dao;

import java.util.List;
import java.util.Optional;

import com.example.lab2.entity.WishList;

public interface WishListDao {
    Long create(WishList wishList);
    Optional<WishList> read(Long userId);
    void update(WishList wishList);
    void delete(Long userId);

    List<WishList> findAllByPostId(Long postId);
}
