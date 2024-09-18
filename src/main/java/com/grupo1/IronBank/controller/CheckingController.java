package com.grupo1.IronBank.controller;

import com.grupo1.IronBank.dtos.CheckingUpdateDTO;
import com.grupo1.IronBank.model.Checking;
import com.grupo1.IronBank.service.CheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checking")
public class CheckingController {

    @Autowired
    private CheckingService checkingService;

    @GetMapping
    public List<Checking> getAllCheckings(){return checkingService.getAllCheckings();}

    @GetMapping("/{id}")
    public ResponseEntity<Checking> getCheckingById(@PathVariable Long id){
        Optional<Checking> checking = checkingService.getCheckingById(id);
        return checking.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Checking createChecking(@RequestBody Checking checking){
        return checkingService.createChecking(checking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChecking(@PathVariable Long id){
        if(checkingService.deleteChecking(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Checking> updateChecking(@PathVariable Long id, @RequestBody Checking checking){
        Optional<Checking> updatedChecking = checkingService.updateChecking(id, checking);
        return updatedChecking.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
}
