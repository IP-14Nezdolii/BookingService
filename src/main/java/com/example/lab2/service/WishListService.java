package com.example.lab2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.lab2.entity.WishList;
import com.example.lab2.entity.post.Post;
import com.example.lab2.entity.post.Post.PostStatus;
import com.example.lab2.repository.PostRepository;
import com.example.lab2.repository.dao.WishListDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final PostRepository postRepository;
    private final WishListDao wishListDao;

    public Optional<Long> addToList(Long userId, Long postId) {
        if (postRepository.existsByIdAndPostStatus(postId, PostStatus.AVAILABLE) &&
            !postRepository.existsByIdAndHousingOwnerId(postId, userId)) {
            
            WishList wishList = null;

            if (wishListDao.read(userId).isPresent()) {
                wishList = wishListDao.read(userId).get();

                if (!wishList.getList().contains(postId))
                    wishList.getList().add(postId);
            }
            else {
                wishList = new WishList(userId, new ArrayList<Long>());
                wishList.getList().add(postId);
            } 
            
            wishListDao.create(wishList);
            return Optional.of(postId);
        }
        return Optional.ofNullable(null);
    }

    public void removeFromList(Long userId, Long postId) {
        wishListDao.delete(userId);
    }

    public List<Post> getWishList(Long userId) {
        List<Post> postList = new ArrayList<Post>();

        if (wishListDao.read(userId).isPresent()) {
            var wishList = wishListDao.read(userId).get();

            List<Long> idList = wishList.getList();

            for (Long postId : idList) {
                var result = postRepository.findByIdAndPostStatus(postId, PostStatus.AVAILABLE);
                if (result.isPresent())
                    postList.add(result.get());
            }
        }
        return postList;
    }

    public Optional<WishList> findWishList(Long userId) {
        return wishListDao.read(userId);
    }

    public boolean delete(Long userId) {
        if (wishListDao.read(userId).isEmpty())
            return false;

        wishListDao.delete(userId);
        return true;
    }
}
