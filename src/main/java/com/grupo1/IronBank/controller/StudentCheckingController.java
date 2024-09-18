package com.grupo1.IronBank.controller;

import com.grupo1.IronBank.model.StudentChecking;
import com.grupo1.IronBank.model.StudentChecking;
import com.grupo1.IronBank.service.StudentCheckingService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/student-checking")
public class StudentCheckingController {

    @Autowired
    private StudentCheckingService studentCheckingService;

    @GetMapping
    public List<StudentChecking> getAllStudentCheckings(){return studentCheckingService.getAllStudentCheckings();}

    @GetMapping("/{id}")
    public ResponseEntity<StudentChecking> getStudentCheckingById(@PathVariable Long id){
        Optional<StudentChecking> studentChecking = studentCheckingService.getStudentCheckingById(id);
        return studentChecking.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentChecking(@PathVariable Long id){
        if(studentCheckingService.deleteStudentChecking(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentChecking> updateStudentChecking(@PathVariable Long id, @RequestBody StudentChecking studentChecking){
        Optional<StudentChecking> updatedStudentChecking = studentCheckingService.updateStudentChecking(id, studentChecking);
        return updatedStudentChecking.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
    
}
