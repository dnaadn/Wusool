package com.example.capstone2.Service;

import com.example.capstone2.Model.PlaceImage;
import com.example.capstone2.Repository.PlaceImageRepository;
import com.example.capstone2.Repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class PlaceImageService {

    private final PlaceImageRepository placeImageRepository;
    private final PlaceRepository placeRepository;



    public List<PlaceImage> getAllPlaceImages(){
        return placeImageRepository.findAll();
    }

    public boolean addPlaceImage(PlaceImage placeImage) {

        if (placeRepository.findPlaceById(placeImage.getPlaceId()) == null) {
            return false;
        }

        placeImageRepository.save(placeImage);
        return true;
    }

    public boolean updatePlaceImage(Integer id, PlaceImage newData) {

        PlaceImage oldData = placeImageRepository.findPlaceImageById(id);

        if (oldData == null) {
            return false;
        }

        if (placeRepository.findPlaceById(newData.getPlaceId()) == null) {
            return false;
        }

        oldData.setPlaceId(newData.getPlaceId());
        oldData.setImageUrl(newData.getImageUrl());

        placeImageRepository.save(oldData);
        return true;
    }

    public boolean deletePlaceImage(Integer id) {

        PlaceImage placeImage = placeImageRepository.findPlaceImageById(id);

        if (placeImage == null) {
            return false;
        }

        placeImageRepository.delete(placeImage);
        return true;
    }
}
