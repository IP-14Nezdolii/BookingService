package com.example.lab.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.lab.entity.booking.Booking;
import com.example.lab.repository.interfaces.BookingRepository;

@Repository
public class FakeBookingRepository implements BookingRepository {
    private final static Map<Long, Booking> BOOKINGS = new HashMap<>();
    private static long idCounter = 0;

    @Override
    public List<Booking> getAll() {
        return new ArrayList<Booking>(BOOKINGS.values());
    }

    @Override
    public List<Booking> getBookingList(String userId) {
        return new ArrayList<Booking>(BOOKINGS.values());
    }

    @Override
    public Optional<Booking> getById(long id) {
        return Optional.ofNullable(BOOKINGS.get(id));
    }

    @Override
    public Optional<Booking> save(Booking booking) {
        booking.setId(idCounter);
        if(BOOKINGS.putIfAbsent(booking.getId(), booking) == null) {
            idCounter++;
            Optional.ofNullable(booking);
        }   
        return Optional.ofNullable(null);
    }

    public boolean setBookingStatus(long id, Booking.BookingStatus status) {
        return true;
    }

    @Override
    public Optional<Booking> update(Booking booking) {
        if(BOOKINGS.containsKey(booking.getId())) {
            BOOKINGS.put(booking.getId(), booking);
            return Optional.ofNullable(booking);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public boolean deleteById(long id) {
        return BOOKINGS.remove(id) != null;
    }
}
