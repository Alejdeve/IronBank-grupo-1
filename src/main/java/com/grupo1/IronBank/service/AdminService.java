package com.grupo1.IronBank.service;

import com.grupo1.IronBank.model.Admin;
import com.grupo1.IronBank.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins(){return adminRepository.findAll();}

    public Optional<Admin> getAdminById(Long id){
        return adminRepository.findById(id);
    }

    public Admin createAdmin(Admin admin){
        return adminRepository.save(admin);
    }

    public boolean deleteAdmin(Long id){
        if(adminRepository.existsById(id)){
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Admin> updateAdmin(Long id, Admin admin){
        if(adminRepository.existsById(id)){
            admin.setId(id);
            return Optional.of(adminRepository.save(admin));
        }
        return Optional.empty();
    }
}
