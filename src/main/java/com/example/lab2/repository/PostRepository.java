package com.example.lab2.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.lab2.entity.post.Address;
import com.example.lab2.entity.post.Housing;
import com.example.lab2.entity.post.Post;
import com.example.lab2.entity.post.Post.PostStatus;


public interface PostRepository extends JpaRepository<Post,Long>, JpaSpecificationExecutor<Post> {
    boolean existsByHousing(Housing housing);
    boolean existsByHousingAddress(Address address);
    
    boolean existsByIdAndPostStatus(Long id, PostStatus postStatus);
    boolean existsByIdAndHousingOwnerId(Long postId, Long ownerId);

    Iterable<Post> findAllByHousingOwnerId(Long id);

    Iterable<Post> findAllByHousingAddress(Address address);

    Iterable<Post> findAllByHousingOwnerIdAndPostStatus(Long ownerId, PostStatus postStatus);
    Optional<Post> findByIdAndPostStatus(Long id, PostStatus postStatus);
}
