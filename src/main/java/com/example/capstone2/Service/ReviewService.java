package com.example.capstone2.Service;

import com.example.capstone2.Model.Booking;
import com.example.capstone2.Model.Place;
import com.example.capstone2.Model.Review;
import com.example.capstone2.Model.User;
import com.example.capstone2.Repository.BookingRepository;
import com.example.capstone2.Repository.PlaceRepository;
import com.example.capstone2.Repository.ReviewRepository;
import com.example.capstone2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final WhatsappService whatsappService;


    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public boolean addReview(Review review) {

        if (userRepository.findUserById(review.getUserId()) == null) {
            return false;
        }

        Booking booking = bookingRepository.findBookingById(review.getBookingId());

        if (booking == null) {
            return false;
        }

        if (!booking.getUserId().equals(review.getUserId())) {
            return false;
        }

        if (!"COMPLETED".equals(booking.getStatus())) {
            return false;
        }

        // one review per booking
        if (reviewRepository.existsByBookingId(review.getBookingId())) {
            return false;
        }

        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
        User user = userRepository.findUserById(review.getUserId());
        Place place = placeRepository.findPlaceById(booking.getPlaceId());

        whatsappService.sendMessage(
                user.getPhone(),
                "Thank you for your review! ⭐\n\n"
                        + "Place: " + place.getName() + "\n"
                        + "Rating: " + review.getRating() + "/5\n\n"
                        + "Your feedback helps improve Wusool."
        );

        return true;
    }

    public boolean updateReview(Integer id, Review newData) {

        Review oldData = reviewRepository.findReviewById(id);

        if (oldData == null) {
            return false;
        }
        // the review cannot be moved to another booking or another user
        if (!oldData.getBookingId().equals(newData.getBookingId())
                || !oldData.getUserId().equals(newData.getUserId())) {
            return false;
        }

        oldData.setRating(newData.getRating());
        oldData.setComment(newData.getComment());

        reviewRepository.save(oldData);
        return true;
    }

    public boolean deleteReview(Integer id) {

        Review review = reviewRepository.findReviewById(id);

        if (review == null) {
            return false;
        }

        reviewRepository.delete(review);
        return true;
    }

    // reviews of a place
    public List<Review> getPlaceReviews(Integer placeId) {

        if (placeRepository.findPlaceById(placeId) == null) {
            return null;
        }

        // a review is linked to a booking, and the booking is linked to the place
        List<Booking> bookings = bookingRepository.findBookingsByPlaceId(placeId);
        List<Review> reviews = new ArrayList<>();

        for (Booking booking : bookings) {
            Review review = reviewRepository.findReviewByBookingId(booking.getId());

            if (review != null) {
                reviews.add(review);
            }
        }

        return reviews;
    }


}
