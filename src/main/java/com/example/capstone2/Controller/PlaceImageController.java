package com.example.capstone2.Controller;

import com.example.capstone2.API.ApiResponse;
import com.example.capstone2.Model.PlaceImage;
import com.example.capstone2.Service.PlaceImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/place-image")
@RequiredArgsConstructor
public class PlaceImageController {

    private final PlaceImageService placeImageService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllPlaceImages() {
        return ResponseEntity.status(200).body(placeImageService.getAllPlaceImages());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPlaceImage(@RequestBody @Valid PlaceImage placeImage, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (placeImageService.addPlaceImage(placeImage)) {
            return ResponseEntity.status(200).body(new ApiResponse("Place image added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not add place image, place not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePlaceImage(@PathVariable Integer id, @RequestBody @Valid PlaceImage placeImage, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (placeImageService.updatePlaceImage(id, placeImage)) {
            return ResponseEntity.status(200).body(new ApiResponse("Place image updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not update place image, check the id and the place id"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlaceImage(@PathVariable Integer id) {

        if (placeImageService.deletePlaceImage(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Place image deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not delete place image, record not found"));
    }
}
