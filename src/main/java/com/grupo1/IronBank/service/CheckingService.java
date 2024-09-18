package com.grupo1.IronBank.service;

import com.grupo1.IronBank.classes.Money;
import com.grupo1.IronBank.model.AccountHolder;
import com.grupo1.IronBank.model.Checking;
import com.grupo1.IronBank.model.StudentChecking;
import com.grupo1.IronBank.repository.AccountHolderRepository;
import com.grupo1.IronBank.repository.CheckingRepository;
import com.grupo1.IronBank.repository.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class CheckingService {

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    public List<Checking> getAllCheckings(){return checkingRepository.findAll();}

    public Optional<Checking> getCheckingById(Long id){
        return checkingRepository.findById(id);
    }

    public Checking createChecking(Checking checking) {
        if (checking.getPrimaryOwner() == null) {
            throw new IllegalArgumentException("Primary owner cannot be null");
        }

        AccountHolder primaryOwner = accountHolderRepository.findById(checking.getPrimaryOwner().getId())
                .orElseThrow(() -> new IllegalArgumentException("Primary owner not found"));
        checking.setPrimaryOwner(primaryOwner);

        if (checking.getSecondaryOwner() != null) {

            AccountHolder secondaryOwner = accountHolderRepository.findById(checking.getSecondaryOwner().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Secondary owner not found"));
            checking.setSecondaryOwner(secondaryOwner);
        }

        long age = ChronoUnit.YEARS.between(primaryOwner.getBirthDate(), LocalDate.now());

        if (age < 24) {
            StudentChecking studentChecking = new StudentChecking();

            studentChecking.setPrimaryOwner(primaryOwner);
            studentChecking.setSecondaryOwner(checking.getSecondaryOwner());
            studentChecking.setBalance(checking.getBalance());
            studentChecking.setSecretKey(checking.getSecretKey());
            studentChecking.setMinimumBalance(new Money(BigDecimal.valueOf(0)));
            studentChecking.setMonthlyMaintenanceFee(new Money(BigDecimal.valueOf(0)));

            return studentCheckingRepository.save(studentChecking);
        }
        checking.setMinimumBalance(new Money(BigDecimal.valueOf(200)));
        checking.setMonthlyMaintenanceFee(new Money(BigDecimal.valueOf(12)));
        return checkingRepository.save(checking);
    }

    public boolean deleteChecking(Long id){
        if(checkingRepository.existsById(id)){
            checkingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Checking> updateChecking(Long id, Checking checking){
        if(checkingRepository.existsById(id)){
            checking.setId(id);
            return Optional.of(checkingRepository.save(checking));
        }
        return Optional.empty();
    }



}
