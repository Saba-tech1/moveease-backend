package com.moveease.service;

import com.moveease.model.Booking;
import com.moveease.model.Booking.BookingStatus;
import com.moveease.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // ── Create ──────────────────────────────────
    public Booking createBooking(Booking booking) {
        booking.setStatus(BookingStatus.PENDING);
        return bookingRepository.save(booking);
    }

    // ── Read ──────────────────────────────────
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }

    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByEmail(email);
    }

    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    public List<Booking> getBookingsByBranch(String branch) {
        return bookingRepository.findByBranch(branch);
    }

    // ── Update Status ──────────────────────────────────
    public Booking updateBookingStatus(Long id, BookingStatus newStatus) {
        Booking booking = getBookingById(id);
        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }

    // ── Delete ──────────────────────────────────
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found with ID: " + id);
        }
        bookingRepository.deleteById(id);
    }
}
