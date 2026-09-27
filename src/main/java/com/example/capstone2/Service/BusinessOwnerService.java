package com.example.capstone2.Service;

import com.example.capstone2.Model.BusinessOwner;
import com.example.capstone2.Repository.BusinessOwnerRepository;
import com.example.capstone2.Repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessOwnerService {

    private final BusinessOwnerRepository businessOwnerRepository;
    private final PlaceRepository placeRepository;


    public List<BusinessOwner> getAllBusinessOwners(){
        return businessOwnerRepository.findAll();
    }

    public boolean addBusinessOwner(BusinessOwner businessOwner){
        if (businessOwnerRepository.existsByEmail(businessOwner.getEmail())){
            return false;
        }
        if(businessOwnerRepository.existsByPhone(businessOwner.getPhone())){
            return false;
        }
        if(businessOwnerRepository.existsByBusinessNameIgnoreCase(businessOwner.getBusinessName())){
            return false;
        }

        businessOwnerRepository.save(businessOwner);
        return true;

    }


    public boolean updateBusinessOwner(Integer id, BusinessOwner newBusinessOwner){
        BusinessOwner oldBusinessOwner = businessOwnerRepository.findBusinessOwnerById(id);
        if (oldBusinessOwner == null) {
            return false;
        }

        if (!oldBusinessOwner.getEmail().equals(newBusinessOwner.getEmail())
                && businessOwnerRepository.existsByEmail(newBusinessOwner.getEmail())) {
            return false;
        }

        if (!oldBusinessOwner.getPhone().equals(newBusinessOwner.getPhone())
                && businessOwnerRepository.existsByPhone(newBusinessOwner.getPhone())) {
            return false;
        }

        if (!oldBusinessOwner.getBusinessName().equals(newBusinessOwner.getBusinessName())
                && businessOwnerRepository.existsByBusinessNameIgnoreCase(newBusinessOwner.getBusinessName())) {
            return false;
        }

        oldBusinessOwner.setName(newBusinessOwner.getName());
        oldBusinessOwner.setEmail(newBusinessOwner.getEmail());
        oldBusinessOwner.setPassword(newBusinessOwner.getPassword());
        oldBusinessOwner.setPhone(newBusinessOwner.getPhone());
        oldBusinessOwner.setBusinessName(newBusinessOwner.getBusinessName());

        businessOwnerRepository.save(oldBusinessOwner);
        return true;
    }

    public boolean deleteBusinessOwner(Integer id ){
        BusinessOwner businessOwner = businessOwnerRepository.findBusinessOwnerById(id);

        if(businessOwner == null){
            return false;
        }
        // an owner who still has places can't be deleted
        if (placeRepository.existsByOwnerId(id)) {
            return false;
        }
        businessOwnerRepository.delete(businessOwner);
        return true;
    }
}
