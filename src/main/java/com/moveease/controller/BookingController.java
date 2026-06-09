package com.moveease.controller;

import com.moveease.model.Booking;
import com.moveease.model.Booking.BookingStatus;
import com.moveease.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) {
        Booking saved = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Booking>> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(bookingService.getBookingsByEmail(email));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> getByStatus(@PathVariable BookingStatus status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    @GetMapping("/branch/{branch}")
    public ResponseEntity<List<Booking>> getByBranch(@PathVariable String branch) {
        return ResponseEntity.ok(bookingService.getBookingsByBranch(branch));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Booking> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        BookingStatus status = BookingStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok(Map.of("message", "Booking deleted successfully"));
    }
}
