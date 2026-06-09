package com.moveease.repository;

import com.moveease.model.Booking;
import com.moveease.model.Booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find bookings by email
    List<Booking> findByEmail(String email);

    // Find by status
    List<Booking> findByStatus(BookingStatus status);

    // Find by service type
    List<Booking> findByServiceType(String serviceType);

    // Find by branch
    List<Booking> findByBranch(String branch);

    // Find bookings for a moving date
    List<Booking> findByMovingDate(LocalDate movingDate);

    // Find by email and status
    List<Booking> findByEmailAndStatus(String email, BookingStatus status);
}