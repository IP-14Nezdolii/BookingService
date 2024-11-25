package com.example.lab.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lab.entity.booking.Booking;
import com.example.lab.entity.booking.BookingDetails;
import com.example.lab.service.booking.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("")
    public ResponseEntity<List<Booking>> getAll() {
        final List<Booking> bookings = bookingService.readAll();

        return !bookings.isEmpty()
           ? new ResponseEntity<>(bookings, HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{booking-id}")
    public ResponseEntity<Booking> get(@PathVariable(name = "booking-id") long bookingId) {
        final Optional<Booking> booking = bookingService.readBooking(bookingId);

        return booking.isPresent()
           ? new ResponseEntity<>(booking.get(), HttpStatus.OK)
           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> create(@RequestBody BookingDetails bookingDetails) {    
        
        final Optional<Booking> booking = bookingService.makeBooking(bookingDetails);

        return booking.isPresent()
           ? new ResponseEntity<>(booking.get(), HttpStatus.CREATED)
           : new ResponseEntity<>(HttpStatus.CONFLICT);
        
    }
}
