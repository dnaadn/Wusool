package com.example.capstone2.Controller;

import com.example.capstone2.API.ApiResponse;
import com.example.capstone2.Model.PlaceCategory;
import com.example.capstone2.Service.PlaceCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/place-category")
@RequiredArgsConstructor
public class PlaceCategoryController {

    private final PlaceCategoryService placeCategoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllPlaceCategories() {
        return ResponseEntity.status(200).body(placeCategoryService.getAllPlaceCategories());
    }

    @PostMapping("/add/{adminId}")
    public ResponseEntity<?> addPlaceCategory(@PathVariable Integer adminId, @RequestBody @Valid PlaceCategory placeCategory, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (placeCategoryService.addPlaceCategory(adminId, placeCategory)) {
            return ResponseEntity.status(200).body(new ApiResponse("Place category added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not add place category, check the admin id and that the name is not already used"));
    }

    @PutMapping("/update/{adminId}/{id}")
    public ResponseEntity<?> updatePlaceCategory(@PathVariable Integer adminId, @PathVariable Integer id, @RequestBody @Valid PlaceCategory placeCategory, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (placeCategoryService.updatePlaceCategory(adminId, id, placeCategory)) {
            return ResponseEntity.status(200).body(new ApiResponse("Place category updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not update place category, check the admin id, category id and name"));
    }

    @DeleteMapping("/delete/{adminId}/{id}")
    public ResponseEntity<?> deletePlaceCategory(@PathVariable Integer adminId, @PathVariable Integer id) {

        if (placeCategoryService.deletePlaceCategory(adminId, id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Place category deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not delete place category,"));
    }
}
