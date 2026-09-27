package com.example.capstone2.Controller;

import com.example.capstone2.API.ApiResponse;
import com.example.capstone2.Model.Booking;
import com.example.capstone2.Service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.status(200).body(bookingService.getAllBookings());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@RequestBody @Valid Booking booking, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (bookingService.addBooking(booking)) {
            return ResponseEntity.status(200).body(new ApiResponse("Booking added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not add booking, check that the user and place exist, the time is inside working hours, not in the past, and not already booked"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Integer id, @RequestBody @Valid Booking booking, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (bookingService.updateBooking(id, booking)) {
            return ResponseEntity.status(200).body(new ApiResponse("Booking updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not update booking, check the booking, the user, the place, the working hours and that the time is free"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Integer id) {

        if (bookingService.deleteBooking(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Booking deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not delete booking, booking not found or it has a review"));
    }

    // extra: booking history of a user (newest first)
    @GetMapping("/user-history/{userId}")
    public ResponseEntity<?> getUserBookingHistory(@PathVariable Integer userId) {

        List<Booking> bookings = bookingService.getUserBookingHistory(userId);

        if (bookings == null) {
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));
        }
        return ResponseEntity.status(200).body(bookings);
    }

    // extra: the owner completes a booking (PENDING -> COMPLETED)
    @PutMapping("/complete/{ownerId}/{bookingId}")
    public ResponseEntity<?> completeBooking(@PathVariable Integer ownerId, @PathVariable Integer bookingId) {

        if (bookingService.completeBooking(ownerId, bookingId)) {
            return ResponseEntity.status(200).body(new ApiResponse("Booking completed successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not complete booking, it must be a PENDING booking in one of your places"));
    }

    // extra: bookings of a place on a specific date
    @GetMapping("/place/{placeId}/{date}")
    public ResponseEntity<?> getPlaceBookings(@PathVariable Integer placeId, @PathVariable String date) {

        LocalDate parsedDate = LocalDate.parse(date);

        List<Booking> bookings = bookingService.getPlaceBookings(placeId, parsedDate);

        if (bookings == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Place not found"));
        }
        return ResponseEntity.status(200).body(bookings);
    }






}
