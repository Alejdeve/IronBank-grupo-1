package com.grupo1.IronBank.service;

import com.grupo1.IronBank.classes.Money;
import com.grupo1.IronBank.model.Savings;
import com.grupo1.IronBank.repository.SavingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class SavingsService {

    @Autowired
    private SavingsRepository savingsRepository;

    public List<Savings> getAllSavings(){return savingsRepository.findAll();}

    public Optional<Savings> getSavingById(Long id){
        return savingsRepository.findById(id);
    }

    public Savings createSavings(Savings savings){
        if (savings.getMinimumBalance() == null ||
                savings.getMinimumBalance().getAmount().compareTo(BigDecimal.valueOf(100)) < 0 ||
                savings.getMinimumBalance().getAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
            savings.setMinimumBalance(new Money(BigDecimal.valueOf(1000)));
        }

        if (savings.getInterestRate() == null ||
                savings.getInterestRate().compareTo(BigDecimal.valueOf(0.0025)) < 0 ||
                savings.getInterestRate().compareTo(BigDecimal.valueOf(0.5)) > 0) {
            savings.setInterestRate(BigDecimal.valueOf(0.0025));
        }

        return savingsRepository.save(savings);
    }

    public boolean deleteSaving(Long id){
        if(savingsRepository.existsById(id)){
            savingsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Savings> updateSavings(Long id, Savings savings){
        if(savingsRepository.existsById(id)){
            savings.setId(id);
            return Optional.of(savingsRepository.save(savings));
        }
        return Optional.empty();
    }

    public void applyInterest(Savings savingsAccount) {
        LocalDate now = LocalDate.now();
        LocalDate lastInterest = savingsAccount.getLastInterestAdded();

        if (lastInterest == null || ChronoUnit.YEARS.between(lastInterest, now) >= 1) {
            BigDecimal interestRate = savingsAccount.getInterestRate();
            Money balance = savingsAccount.getBalance();

            BigDecimal interest = balance.getAmount().multiply(interestRate);
            balance.setAmount(balance.getAmount().add(interest));

            savingsAccount.setBalance(balance);
            savingsAccount.setLastInterestAdded(now);

            savingsRepository.save(savingsAccount);
        }
    }
}
