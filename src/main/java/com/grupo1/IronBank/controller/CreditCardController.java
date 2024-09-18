package com.grupo1.IronBank.controller;

import com.grupo1.IronBank.classes.Money;
import com.grupo1.IronBank.model.CreditCard;
import com.grupo1.IronBank.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit-card")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @GetMapping
    public List<CreditCard> getAllCreditCards(){return creditCardService.getAllCreditCards();}

    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable Long id){
        Optional<CreditCard> creditCard = creditCardService.getCreditCardById(id);
        return creditCard.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Money> getBalance(@PathVariable Long id) {
        Optional<CreditCard> optionalCreditCard = creditCardService.getCreditCardById(id);

        if (optionalCreditCard.isPresent()) {
            CreditCard creditCard = optionalCreditCard.get();

            creditCardService.applyInterest(creditCard);

            return ResponseEntity.ok(creditCard.getBalance());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public CreditCard createCreditCard(@RequestBody CreditCard creditCard){
        return creditCardService.createCreditCard(creditCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable Long id){
        if(creditCardService.deleteCreditCard(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCard> updateCreditCard(@PathVariable Long id, @RequestBody CreditCard creditCard){
        Optional<CreditCard> updatedCreditCard = creditCardService.updateCreditCard(id, creditCard);
        return updatedCreditCard.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
}
