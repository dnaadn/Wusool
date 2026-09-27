package com.example.capstone2.Service;

import com.example.capstone2.Model.PlaceAccessibility;
import com.example.capstone2.Repository.PlaceAccessibilityRepository;
import com.example.capstone2.Repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceAccessibilityService {

    private final PlaceAccessibilityRepository placeAccessibilityRepository;
    private final PlaceRepository placeRepository;

    public List<PlaceAccessibility> getAllPlaceAccessibility(){
        return placeAccessibilityRepository.findAll();
    }

    public boolean addPlaceAccessibility(PlaceAccessibility placeAccessibility) {

        if (placeRepository.findPlaceById(placeAccessibility.getPlaceId()) == null) {
            return false;
        }

        placeAccessibilityRepository.save(placeAccessibility);
        return true;
    }

    public boolean updatePlaceAccessibility(Integer id, PlaceAccessibility newData) {

        PlaceAccessibility oldData = placeAccessibilityRepository.findPlaceAccessibilityById(id);

        if (oldData == null) {
            return false;
        }

        if (placeRepository.findPlaceById(newData.getPlaceId()) == null) {
            return false;
        }

        oldData.setPlaceId(newData.getPlaceId());
        oldData.setAccessibilityType(newData.getAccessibilityType());
        oldData.setDescription(newData.getDescription());

        placeAccessibilityRepository.save(oldData);

        return true;
    }

    public boolean deletePlaceAccessibility(Integer id) {

        PlaceAccessibility placeAccessibility = placeAccessibilityRepository.findPlaceAccessibilityById(id);

        if (placeAccessibility == null) {
            return false;
        }

        placeAccessibilityRepository.delete(placeAccessibility);
        return true;
    }
}
