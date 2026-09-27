package com.example.capstone2.Controller;

import com.example.capstone2.API.ApiResponse;
import com.example.capstone2.Model.Place;
import com.example.capstone2.Service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
public class PlaceController {


    private final PlaceService placeService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllPlaces() {
        return ResponseEntity.status(200).body(placeService.getAllPlaces());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPlace(@RequestBody @Valid Place place, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (placeService.addPlace(place)) {
            return ResponseEntity.status(200).body(new ApiResponse("Place added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not add place"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePlace(@PathVariable Integer id, @RequestBody @Valid Place place, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (placeService.updatePlace(id, place)) {
            return ResponseEntity.status(200).body(new ApiResponse("Place updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not update place"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable Integer id) {

        if (placeService.deletePlace(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Place deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not delete place, place not found or it has bookings"));
    }

    // extra : places visited by a user
    @GetMapping("/visited-by/{userId}")
    public ResponseEntity<?> getPlacesVisitedByUser(@PathVariable Integer userId) {

        List<Place> places = placeService.getPlacesVisitedByUser(userId);

        if (places == null) {
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));
        }
        return ResponseEntity.status(200).body(places);
    }

    // extra : places by city
    @GetMapping("/city/{city}")
    public ResponseEntity<?> getPlacesByCity(@PathVariable String city) {
        return ResponseEntity.status(200).body(placeService.getPlacesByCity(city));
    }

    // extra : places by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getPlacesByCategory(@PathVariable Integer categoryId) {

        List<Place> places = placeService.getPlacesByCategory(categoryId);

        if (places == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
        }
        return ResponseEntity.status(200).body(places);
    }

    // extra : places that support an accessibility type
    @GetMapping("/accessibility/{type}")
    public ResponseEntity<?> getPlacesByAccessibility(@PathVariable String type) {
        return ResponseEntity.status(200).body(placeService.getPlacesByAccessibility(type));
    }

    // extra : places open now
    @GetMapping("/open-now")
    public ResponseEntity<?> getOpenPlacesNow() {
        return ResponseEntity.status(200).body(placeService.getOpenPlacesNow());
    }

    //extra : return available locations based on a specific time determined by the user.
    @GetMapping("/available/{time}")
    public List<Place> getAvailablePlaces(@PathVariable LocalTime time) {
        return placeService.getAvailablePlaces(time);
    }

    //extra : return the most visited "booked" place
    @GetMapping("/most-visited")
    public List<Place> getMostVisitedPlaces() {
        return placeService.getMostVisitedPlaces();
    }

    // extra : search places by city + category name
    @GetMapping("/search/{city}/{category}")
    public ResponseEntity<?> searchPlaces(@PathVariable String city, @PathVariable String category) {
        return ResponseEntity.status(200).body(placeService.searchPlaces(city, category));
    }

}
