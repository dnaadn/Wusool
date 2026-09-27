package com.example.capstone2.Service;

import com.example.capstone2.Model.Admin;
import com.example.capstone2.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public boolean addAdmin(Admin admin){
        if(adminRepository.existsByEmail(admin.getEmail())){
            return false;
        }
        if(adminRepository.existsByPhone(admin.getPhone())){
            return false;
        }

        adminRepository.save(admin);
        return true;

    }

    public boolean updateAdmin(Integer id, Admin newAdmin){
        Admin oldAdmin = adminRepository.findAdminById(id);

        if(oldAdmin == null){
            return false;
        }
        if (!oldAdmin.getEmail().equals(newAdmin.getEmail())
                && adminRepository.existsByEmail(newAdmin.getEmail())) {
            return false;
        }

        if (!oldAdmin.getPhone().equals(newAdmin.getPhone())
                && adminRepository.existsByPhone(newAdmin.getPhone())) {
            return false;
        }

        oldAdmin.setName(newAdmin.getName());
        oldAdmin.setEmail(newAdmin.getEmail());
        oldAdmin.setPassword(newAdmin.getPassword());
        oldAdmin.setPhone(newAdmin.getPhone());

        adminRepository.save(oldAdmin);
        return true;
    }

    public boolean deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminById(id);

        if(admin == null){
            return false;
        }
        adminRepository.delete(admin);
        return true;
    }


}
