package com.grupo1.IronBank.controller;

import com.grupo1.IronBank.model.AccountHolder;
import com.grupo1.IronBank.service.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("account-holder")
public class AccountHolderController {

    @Autowired
    private AccountHolderService accountHolderService;

    @GetMapping
    public List<AccountHolder> getAllAccountHolders(){return accountHolderService.getAllAccountHolders();}

    @GetMapping("/{id}")
    public ResponseEntity<AccountHolder> getAccountHolderById(@PathVariable Long id){
        Optional<AccountHolder> accountHolder = accountHolderService.getAccountHolderById(id);
        return accountHolder.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AccountHolder createAccountHolder(@RequestBody AccountHolder accountHolder){
        return accountHolderService.createAccountHolder(accountHolder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountHolder(@PathVariable Long id){
        if(accountHolderService.deleteAccountHolder(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountHolder> updateAccountHolder(@PathVariable Long id, @RequestBody AccountHolder accountHolder){
        Optional<AccountHolder> updatedAccountHolder = accountHolderService.updateAccountHolder(id, accountHolder);
        return updatedAccountHolder.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
}
