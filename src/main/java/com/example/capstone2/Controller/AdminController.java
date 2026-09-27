package com.example.capstone2.Controller;

import com.example.capstone2.API.ApiResponse;
import com.example.capstone2.Model.Admin;
import com.example.capstone2.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllAdmins() {
        return ResponseEntity.status(200).body(adminService.getAllAdmins());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody @Valid Admin admin, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (adminService.addAdmin(admin)) {
            return ResponseEntity.status(200).body(new ApiResponse("Admin added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not add admin, email or phone already exists"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @RequestBody @Valid Admin admin, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if (adminService.updateAdmin(id, admin)) {
            return ResponseEntity.status(200).body(new ApiResponse("Admin updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not update admin, check the admin id, email and phone"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id) {

        if (adminService.deleteAdmin(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Admin deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Could not delete admin, admin not found"));
    }


}
