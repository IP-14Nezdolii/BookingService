package com.example.lab2;

import java.time.LocalDate;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.lab2.entity.Review;
import com.example.lab2.entity.User;
import com.example.lab2.entity.booking.BookingDetails;
import com.example.lab2.entity.post.Address;
import com.example.lab2.entity.post.Housing;
import com.example.lab2.entity.post.Post;
import com.example.lab2.service.booking.BookingService;
import com.example.lab2.service.post.PostService;
import com.example.lab2.service.review.ReviewService;
import com.example.lab2.service.user.UserService;

import lombok.RequiredArgsConstructor;

/**
 * loads test data
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final UserService userService;
    private final PostService postService;
    private final BookingService bookingService;
    private final ReviewService reviewService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadUserData();
        loadPostData();
        loadBookingData();
        loadReviewData();
    }

    private void loadUserData() {
        User[] users = {
            User.builder()
                .email("adwdr@gmail.com")
                .password("asd11213")
                .name("Ivan")
                .surname("Ovcharenki")
                .cardCode("1111111111111111")
                .build(),

            User.builder()
                .email("mmmmm@gmail.com")
                .password("asd34213")
                .name("Petro")
                .surname("Pismenniy")
                .cardCode("1111112311111111")
                .build(),
        };
        
        for (User user : users) {
            userService.createUser(user);
        }
    }

    /**
    * loads test photo
    */
    @SuppressWarnings("null")
    private MultipartFile getPhoto() {
        ClassPathResource resource = new ClassPathResource("static/" + "example.jpeg");
        
        if (!resource.exists()) 
            throw new RuntimeException("file not exists");

        try {
            return new MockMultipartFile(
                resource.getFilename(),     // file name
                resource.getFilename(),     // original file name
                "image/jpeg",   // MIME-type
                resource.getInputStream()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void loadPostData() {
        Address[] addresses = {
            new Address(
                "133", 
                "Kvitkova", 
                "Kyiv", 
                "Kyivska Oblast", 
                "04219"),
            new Address(
                "134", 
                "Kvitkova", 
                "Kyiv", 
                "Kyivska Oblast", 
                "04219"),
            new Address(
                "135", 
                "Kvitkova", 
                "Kyiv", 
                "Kyivska Oblast", 
                "04219"),
        };
        
        Housing[] housings = {
            new Housing(
                1L, 
                "Nice House", 
                "FFFFFFF", 
                20, 
                addresses[0], 
                Housing.HousingType.HOUSE),
            new Housing(
                1L, 
                "Nice APARTMENT", 
                "LLLLL", 
                20, 
                addresses[1], 
                Housing.HousingType.HOUSE),
            new Housing(
                1L, 
                "Nice APARTMENT", 
                "LLLLL", 
                20, 
                addresses[2], 
                Housing.HousingType.APARTMENT),
        };
              
        Post[] posts = {
            Post.builder()
                .housing(housings[0])
                .costPerDay(50f)
                .minDate(LocalDate.of(2004, 10, 2))
                .maxDate(LocalDate.of(2030, 10, 2))
                .build(),

            Post.builder()
                .housing(housings[1])
                .costPerDay(25f)
                .minDate(LocalDate.of(2004, 10, 2))
                .maxDate(LocalDate.of(2026, 10, 2))
                .build(),

            Post.builder()
                .housing(housings[2])
                .costPerDay(25f)
                .minDate(LocalDate.of(2004, 2, 2))
                .maxDate(LocalDate.of(2026, 12, 2))
                .build(),
        };
       
        for (Post post : posts) {
            postService.createPost(post, getPhoto());
        }      
    }

    private void loadBookingData() {
        BookingDetails[] bookings = {
            new BookingDetails(
                1L, 
                2L, 
                LocalDate.of(2020, 2, 2), 
                LocalDate.of(2020, 10, 2)),
        };

        for (BookingDetails details : bookings) {
            bookingService.makeBooking(details);
        }
    }

    private void loadReviewData() {
        Review[] reviews = {
            Review.builder()
                .authorId(2L)
                .bookingId(1L)
                .description("aaaaaa")
                .rate((byte)10)
                .build()
        };

        for (Review review : reviews) {
            reviewService.createReview(review);
        }
    }
}
