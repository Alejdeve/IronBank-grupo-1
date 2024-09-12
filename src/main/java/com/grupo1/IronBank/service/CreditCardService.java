package com.grupo1.IronBank.service;

import com.grupo1.IronBank.classes.Money;
import com.grupo1.IronBank.model.CreditCard;
import com.grupo1.IronBank.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

    public List<CreditCard> getAllCreditCards(){return creditCardRepository.findAll();}

    public Optional<CreditCard> getCreditCardById(Long id){
        return creditCardRepository.findById(id);
    }

    public CreditCard createCreditCard(CreditCard creditCard){
        if (creditCard.getCreditLimit() == null ||
                creditCard.getCreditLimit().getAmount().compareTo(BigDecimal.valueOf(100)) < 0 ||
                creditCard.getCreditLimit().getAmount().compareTo(BigDecimal.valueOf(100000)) > 0) {
            creditCard.setCreditLimit(new Money(BigDecimal.valueOf(100)));
        }

        if (creditCard.getInterestRate() == null ||
                creditCard.getInterestRate().compareTo(BigDecimal.valueOf(0.1)) < 0 ||
                creditCard.getInterestRate().compareTo(BigDecimal.valueOf(0.2)) > 0) {
            creditCard.setInterestRate(BigDecimal.valueOf(0.2));
        }

        return creditCardRepository.save(creditCard);
    }

    public boolean deleteCreditCard(Long id){
        if(creditCardRepository.existsById(id)){
            creditCardRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<CreditCard> updateCreditCard(Long id, CreditCard creditCard){
        if(creditCardRepository.existsById(id)){
            creditCard.setId(id);
            return Optional.of(creditCardRepository.save(creditCard));
        }
        return Optional.empty();
    }
}
