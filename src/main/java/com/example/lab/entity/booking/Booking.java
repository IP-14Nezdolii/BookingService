package com.example.lab.entity.booking;

import java.time.LocalDate;

import com.example.lab.entity.post.Post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Booking {
    private long id;
    BookingDetails bookingDetails;
    private LocalDate bookingDate;
    private float totalCost;
    private BookingStatus status;

    public static Booking newBooking(BookingDetails bookingDetails, float totalCost) {
        return new Booking(-1, bookingDetails, LocalDate.now(), totalCost, BookingStatus.PROCESSED);
    }

    public static Booking newBooking(BookingDetails bookingDetails, Post post) {
        return newBooking(bookingDetails, bookingDetails.getDays() * post.getCostPerDay());
    }

    public long getDays() {
        return bookingDetails.getDays();
    }

    public enum BookingStatus {
        PROCESSED,
        ACTIVE,
        INACTIVE,
        CANCELED
    }
}
