package com.example.lab2.service.post;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.lab2.entity.Review;
import com.example.lab2.entity.booking.Booking;
import com.example.lab2.entity.booking.Booking.BookingStatus;
import com.example.lab2.entity.post.Post;
import com.example.lab2.entity.post.Post.PostStatus;
import com.example.lab2.repository.BookingRepository;
import com.example.lab2.repository.PostRepository;
import com.example.lab2.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostUpdater {
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;

    public Optional<Post> updatePost(Post post) {
        if (postRepository.findById(post.getId()).isEmpty())
            return Optional.ofNullable(null);

        Post oldPost = postRepository.findById(post.getId()).get();

        //check status
        if (!isUpdateablePostStatus(oldPost.getId(), post.getPostStatus()))
            return Optional.ofNullable(null);             
        //check owner
        if (!(oldPost.getHousing().getOwnerId().equals(post.getHousing().getOwnerId()))) 
            return Optional.ofNullable(null);
        //check housing  
        if (!(oldPost.getHousing().getAddress().equals(post.getHousing().getAddress()))) 
            return Optional.ofNullable(null);
        //check score 
        if (!(oldPost.getScore().equals(post.getScore()))) 
            return Optional.ofNullable(null);

        return Optional.of(postRepository.save(post));
    }

    public void updatePostScore(Long postId) {
        List<Review> reviews = (List<Review>)reviewRepository.findAllByPostId(postId);

        if (reviews.isEmpty())
            return;
        
        int total = 0;
        int num = 0;
        for (Review review : reviews) {
            total+= review.getRate();
            num++;  
        }
 
        if(postRepository.findById(postId).isEmpty())
            return;         
        Post post = postRepository.findById(postId).get();
      
        post.setScore((byte)(total/num));
        postRepository.save(post);
    }

    public boolean isUpdateablePostStatus(Long postId, PostStatus status) {

        if (postRepository.findById(postId).isEmpty())
            return false;

        Post post = postRepository.findById(postId).get();

        if (post.getPostStatus() == status)
            return true;
        if (post.getPostStatus() == PostStatus.ON_DELETE)
            return false;

        if (status != PostStatus.AVAILABLE && 
            status != PostStatus.UNAVAILABLE) 
            return false;
        if (status == PostStatus.UNAVAILABLE)
            if (postHasCurrentBooking(post.getId()))
                return false;

        return true;
    }

    public boolean postHasCurrentBooking(Long postId) {
        List<Booking> bookings = (List<Booking>)bookingRepository.findAllByBookingDetailsPostId(postId);
        for (Booking booking : bookings) 
            if (booking.getStatus() == BookingStatus.ACTIVE ||
                booking.getStatus() == BookingStatus.PROCESSED) 
                return true;
        return false;
    }
}
