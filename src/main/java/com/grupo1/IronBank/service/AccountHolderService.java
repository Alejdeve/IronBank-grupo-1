package com.grupo1.IronBank.service;

import com.grupo1.IronBank.model.AccountHolder;
import com.grupo1.IronBank.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    public List<AccountHolder> getAllAccountHolders(){return accountHolderRepository.findAll();}

    public Optional<AccountHolder> getAccountHolderById(Long id){
        return accountHolderRepository.findById(id);
    }

    public AccountHolder createAccountHolder(AccountHolder accountHolder){
        return accountHolderRepository.save(accountHolder);
    }

    public boolean deleteAccountHolder(Long id){
        if(accountHolderRepository.existsById(id)){
            accountHolderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<AccountHolder> updateAccountHolder(Long id, AccountHolder accountHolder){
        if(accountHolderRepository.existsById(id)){
            accountHolder.setId(id);
            return Optional.of(accountHolderRepository.save(accountHolder));
        }
        return Optional.empty();
    }
}
