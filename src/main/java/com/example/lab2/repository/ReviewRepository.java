package com.example.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lab2.entity.Review;
import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> { 
    Iterable<Review> findAllByPostId(Long id);

    Optional<Review> findByBookingId(Long bookingId);

    boolean existsByBookingId(Long bookingId);
}
