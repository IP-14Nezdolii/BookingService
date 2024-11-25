package com.example.lab.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.example.lab.entity.booking.Booking;

public interface BookingRepository {
    public List<Booking> getAll();

    public Optional<Booking> getById(long id);
    public Optional<Booking> save(Booking booking);
    public Optional<Booking> update(Booking booking);

    public boolean setBookingStatus(long id, Booking.BookingStatus status);
    
    public boolean deleteById(long id);

    public List<Booking> getBookingList(String userId);
}
