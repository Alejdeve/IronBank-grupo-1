package com.grupo1.IronBank.service;

import com.grupo1.IronBank.model.StudentChecking;
import com.grupo1.IronBank.repository.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCheckingService {
    
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    public List<StudentChecking> getAllStudentCheckings(){return studentCheckingRepository.findAll();}

    public Optional<StudentChecking> getStudentCheckingById(Long id){
        return studentCheckingRepository.findById(id);
    }

    public boolean deleteStudentChecking(Long id){
        if(studentCheckingRepository.existsById(id)){
            studentCheckingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<StudentChecking> updateStudentChecking(Long id, StudentChecking studentChecking){
        if(studentCheckingRepository.existsById(id)){
            studentChecking.setId(id);
            return Optional.of(studentCheckingRepository.save(studentChecking));
        }
        return Optional.empty();
    }
}
