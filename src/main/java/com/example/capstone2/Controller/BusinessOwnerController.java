package com.example.capstone2.Controller;

import com.example.capstone2.API.ApiResponse;
import com.example.capstone2.Model.BusinessOwner;
import com.example.capstone2.Service.BusinessOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/business-owner")
@RequiredArgsConstructor
public class BusinessOwnerController {

    private final BusinessOwnerService businessOwnerService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllBusinessOwners() {
        return ResponseEntity.status(200).body(businessOwnerService.getAllBusinessOwners());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBusinessOwner(@RequestBody @Valid BusinessOwner businessOwner, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (businessOwnerService.addBusinessOwner(businessOwner)) {
            return ResponseEntity.status(200).body(new ApiResponse("Business owner added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not add business owner, email, phone or business name already exists"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBusinessOwner(@PathVariable Integer id, @RequestBody @Valid BusinessOwner businessOwner, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (businessOwnerService.updateBusinessOwner(id, businessOwner)) {
            return ResponseEntity.status(200).body(new ApiResponse("Business owner updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not update business owner"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBusinessOwner(@PathVariable Integer id) {

        if (businessOwnerService.deleteBusinessOwner(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Business owner deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not delete business owner, owner not found or still has places"));
    }
}
