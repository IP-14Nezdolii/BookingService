
package com.example.lab.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.example.lab.entity.Review;

public interface ReviewRepository {
    public List<Review> getAll();

    public Optional<Review> getById(long id);
    public Optional<Review> save(Review rewiew);
    public Optional<Review> update(Review rewiew);

    public boolean deleteById(long id);
    
    public List<Review> getPostReviews(long housingId);
}
