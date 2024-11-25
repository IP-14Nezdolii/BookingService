package com.example.lab.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.lab.entity.Review;
import com.example.lab.repository.interfaces.ReviewRepository;

@Repository
public class FakeReviewRepository implements ReviewRepository {
    private final static Map<Long, Review> REVIEWS = new HashMap<>();
    private static long idCounter = 0;

    @Override
    public List<Review> getAll() {
        return new ArrayList<Review>(REVIEWS.values());
    }

    @Override
    public Optional<Review> getById(long id) {
        return Optional.ofNullable(REVIEWS.get(id));
    }

    @Override
    public Optional<Review> save(Review rewiew) {
        rewiew.setId(idCounter);
        if(REVIEWS.putIfAbsent(rewiew.getId(), rewiew) == null) {
            idCounter++;
            return Optional.ofNullable(rewiew);
        }   
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<Review> update(Review rewiew) {
        if(REVIEWS.containsKey(rewiew.getId())) {
            REVIEWS.put(rewiew.getId(), rewiew);
            return Optional.ofNullable(rewiew);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public boolean deleteById(long id) {
        return REVIEWS.remove(id) != null;
    }

    @Override
    public List<Review> getPostReviews(long postId) {
        List<Review> reviews = new ArrayList<>();
        for (Review review : REVIEWS.values()) {
            if (review.getPostId() == postId) {
                reviews.add(review);
            }
        }
        return reviews;
    }
}
