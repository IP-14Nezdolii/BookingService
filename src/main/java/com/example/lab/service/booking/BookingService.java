package com.example.lab.service.booking;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.lab.entity.booking.Booking;
import com.example.lab.entity.booking.BookingDetails;
import com.example.lab.repository.interfaces.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMaker bookingMaker;

    public Optional<Booking> makeBooking(BookingDetails bookingDetails) {
        return bookingMaker.makeBooking(bookingDetails);    
    }

    public List<Booking> getBookingList(String userId) {
        return bookingRepository.getBookingList(userId);
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    public List<Booking> readAll() {
        return bookingRepository.getAll();
    }

    public Optional<Booking> readBooking(long id) {
        return bookingRepository.getById(id);
    }

    public boolean deleteBooking(long id) {
        return bookingRepository.deleteById(id);
    }

    public Optional<Booking> updateBooking(Booking booking) {
        return bookingRepository.update(booking);
    }
}
