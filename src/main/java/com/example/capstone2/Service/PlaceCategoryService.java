package com.example.capstone2.Service;

import com.example.capstone2.Model.PlaceCategory;
import com.example.capstone2.Repository.AdminRepository;
import com.example.capstone2.Repository.PlaceCategoryRepository;
import com.example.capstone2.Repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceCategoryService {

    private final PlaceCategoryRepository placeCategoryRepository;
    private final PlaceRepository placeRepository;
    private final AdminRepository adminRepository;


    public List<PlaceCategory> getAllPlaceCategories(){
        return placeCategoryRepository.findAll();
    }

    public boolean addPlaceCategory(Integer adminId, PlaceCategory placeCategory) {

        // only an admin can manage categories
        if (adminRepository.findAdminById(adminId) == null) {
            return false;
        }

        if (placeCategoryRepository.existsByNameIgnoreCase(placeCategory.getName())) {
            return false;
        }

        placeCategoryRepository.save(placeCategory);
        return true;
    }

    public boolean updatePlaceCategory(Integer adminId, Integer id, PlaceCategory newData) {

        if (adminRepository.findAdminById(adminId) == null) {
            return false;
        }

        PlaceCategory oldData = placeCategoryRepository.findPlaceCategoryById(id);

        if (oldData == null) {
            return false;
        }

        if (!oldData.getName().equalsIgnoreCase(newData.getName())
                && placeCategoryRepository.existsByNameIgnoreCase(newData.getName())) {
            return false;
        }

        oldData.setName(newData.getName());
        oldData.setDescription(newData.getDescription());

        placeCategoryRepository.save(oldData);

        return true;
    }

    public boolean deletePlaceCategory(Integer adminId, Integer id) {

        if (adminRepository.findAdminById(adminId) == null) {
            return false;
        }

        PlaceCategory placeCategory = placeCategoryRepository.findPlaceCategoryById(id);

        if (placeCategory == null) {
            return false;
        }

        // a category used by places can't be deleted
        if (placeRepository.existsByCategoryId(id)) {
            return false;
        }

        placeCategoryRepository.delete(placeCategory);
        return true;
    }
}
