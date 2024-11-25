package com.example.lab.service.booking;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.lab.entity.booking.Booking;
import com.example.lab.entity.booking.BookingDetails;
import com.example.lab.entity.post.Post;
import com.example.lab.repository.interfaces.BookingRepository;
import com.example.lab.repository.interfaces.PostRepository;

import lombok.RequiredArgsConstructor;

@Component
//@Scope("prototype") //DELETE
@RequiredArgsConstructor
public class BookingMaker {
    private final BookingRepository bookingRepository;
    private final PostRepository postRepository;

    Optional<Booking> makeBooking(BookingDetails bookingDetails) {

        Optional<Post> checkedPost = getAvaliablePost(bookingDetails);

        if (checkedPost.isPresent()) {
            Post post = checkedPost.get();
            postRepository.setAvailable(false, post.getId());

            var booking = Booking.newBooking(bookingDetails, post);
            bookingRepository.save(booking);
            
            return Optional.ofNullable(booking);
        }
        else {
            return Optional.ofNullable(null);
        }
    }

    Optional<Post> getAvaliablePost(BookingDetails bookingDetails) {
        Optional<Post> getResult = postRepository.getById(bookingDetails.getPostId());

        if (getResult.isPresent() == false) {
            return Optional.ofNullable(null);
        }

        Post post = getResult.get();

        if (post.getHousing().getOwnerId().equals(bookingDetails.getUserId())) {
            return Optional.ofNullable(null);
        }
        else if (post.isAvailable() && bookingDetails.isDataConsistencyTo(post)) {
            return Optional.ofNullable(post);
        }
        else return Optional.ofNullable(null);
    }
}