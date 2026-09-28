package com.example.capstone2.Controller;

import com.example.capstone2.API.ApiResponse;
import com.example.capstone2.Model.Review;
import com.example.capstone2.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.status(200).body(reviewService.getAllReviews());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody @Valid Review review, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (reviewService.addReview(review)) {
            return ResponseEntity.status(200).body(new ApiResponse("Review added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not add review."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id, @RequestBody @Valid Review review, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (reviewService.updateReview(id, review)) {
            return ResponseEntity.status(200).body(new ApiResponse("Review updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not update review, check the id, and that the booking and user match the original review"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {

        if (reviewService.deleteReview(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Review deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not delete review, review not found"));
    }

    // extra: reviews of a place
    @GetMapping("/place/{placeId}")
    public ResponseEntity<?> getPlaceReviews(@PathVariable Integer placeId) {

        List<Review> reviews = reviewService.getPlaceReviews(placeId);

        if (reviews == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Place not found"));
        }
        return ResponseEntity.status(200).body(reviews);
    }

    // extra: average rating of a place
    @GetMapping("/place/{placeId}/average")
    public ResponseEntity<?> getPlaceAverageRating(@PathVariable Integer placeId) {

        String message = reviewService.getPlaceAverageRating(placeId);

        if (message == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Place not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse(message));
    }
}
