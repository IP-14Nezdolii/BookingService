package com.example.lab;

import java.time.LocalDate;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.lab.entity.User;
import com.example.lab.entity.booking.BookingDetails;
import com.example.lab.entity.post.Address;
import com.example.lab.entity.post.Housing;
import com.example.lab.entity.post.Post;
import com.example.lab.service.booking.BookingService;
import com.example.lab.service.post.PostService;
import com.example.lab.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final UserService userService;
    private final PostService postService;
    private final BookingService bookingService;

    @Override
    public void run(ApplicationArguments args) {
        loadUserData();
        loadPostData();
        loadBookingData();
    }

    void loadUserData() {
        User[] users = {
            new User("adwdr@gmail.com", "asd11213", "Ivan" , "Ovcharenki", (byte)-1),
            new User("mmmmm@gmail.com", "asd34213", "Petro", "Pismenniy", (byte)-1),
        };
        
        for (User user : users) {
            userService.createUser(user);
        }
    }

    void loadPostData() {
        Address[] addresses = {
            new Address(
                133, 
                "Kvitkova", 
                "Kyiv", 
                "Kyivska Oblast", 
                "04219"),
            new Address(
                134, 
                "Kvitkova", 
                "Kyiv", 
                "Kyivska Oblast", 
                "04219"),
        };
        
        Housing[] housings = {
            new Housing(
                "adwdr@gmail.com", 
                "Nice House", 
                "FFFFFFF", 
                (short) 20, 
                addresses[0], 
                Housing.HousingType.HOUSE),
            new Housing(
                "adwdr@gmail.com", 
                "Nice APARTMENT", 
                "LLLLL", 
                (short) 20, 
                addresses[1], 
                Housing.HousingType.APARTMENT),
        };
              
        Post[] posts = {
            new Post(
                0, 
                housings[0], 
                true, 
                50f, 
                LocalDate.now(), 
                LocalDate.of(2030, 10, 2)),
            new Post(
                1, 
                housings[1], 
                true, 
                25f, 
                LocalDate.now(), 
                LocalDate.of(2026, 10, 2)),
        };
       
        for (Post post : posts) {
            postService.createPost(post);
        }      
    }

    void loadBookingData() {
        BookingDetails[] bookings = {
            new BookingDetails(
                0, 
                "mmmmm@gmail.com", 
                LocalDate.now(), 
                LocalDate.of(2025, 10, 2)),
        };

        for (BookingDetails details : bookings) {
            bookingService.makeBooking(details);
        }
    }
}
