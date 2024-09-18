package com.grupo1.IronBank.controller;

import com.grupo1.IronBank.classes.Money;
import com.grupo1.IronBank.model.Savings;
import com.grupo1.IronBank.service.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/savings")
public class SavingsController {

    @Autowired
    private SavingsService savingsService;

    @GetMapping
    public List<Savings> getAllSavings(){return savingsService.getAllSavings();}

    @GetMapping("/{id}")
    public ResponseEntity<Savings> getSavingById(@PathVariable Long id){
        Optional<Savings> savings = savingsService.getSavingById(id);
        return savings.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Money> getBalance(@PathVariable Long id) {
        Optional<Savings> optionalSavings = savingsService.getSavingById(id);

        if (optionalSavings.isPresent()) {
            Savings savingsAccount = optionalSavings.get();

            savingsService.applyInterest(savingsAccount);

            return ResponseEntity.ok(savingsAccount.getBalance());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Savings createSaving(@RequestBody Savings savings){
        return savingsService.createSavings(savings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaving(@PathVariable Long id){
        if(savingsService.deleteSaving(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Savings> updateSaving(@PathVariable Long id, @RequestBody Savings savings){
        Optional<Savings> updatedSaving = savingsService.updateSavings(id, savings);
        return updatedSaving.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
}
