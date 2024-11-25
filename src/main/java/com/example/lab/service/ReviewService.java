package com.example.lab.service;

import org.springframework.stereotype.Service;

import com.example.lab.entity.Review;
import com.example.lab.entity.booking.Booking;
import com.example.lab.repository.interfaces.BookingRepository;
import com.example.lab.repository.interfaces.ReviewRepository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;

    public Optional<Review> createReview(Review review) {
        List<Booking> lst = bookingRepository.getBookingList(review.getAuthorId());
        //make it better

        if (review.getRate() > 5 || review.getRate() < 0) 
            return Optional.ofNullable(null);

        for (Booking booking : lst) {
            if (booking.getBookingDetails().getPostId() == review.getPostId() &&
                booking.getBookingDetails().getUserId().equals(review.getAuthorId())) {
                return reviewRepository.save(review);
            }
        }

        return Optional.ofNullable(null);
    }

    public List<Review> readPostReviews(long postId) {
        return reviewRepository.getPostReviews(postId);
    }

    /////////////////////////////////////////////////////////////////
    public List<Review> readAll() {
        return reviewRepository.getAll();
    }

    public Optional<Review> readReview(long id) {
        return reviewRepository.getById(id);
    }

    public boolean deleteReview(long id) {
        return reviewRepository.deleteById(id);
    }
}
