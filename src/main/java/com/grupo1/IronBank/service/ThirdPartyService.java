package com.grupo1.IronBank.service;

import com.grupo1.IronBank.model.ThirdParty;
import com.grupo1.IronBank.repository.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThirdPartyService {

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    public List<ThirdParty> getAllThirdParties(){return thirdPartyRepository.findAll();}

    public Optional<ThirdParty> getThirdPartyById(Long id){
        return thirdPartyRepository.findById(id);
    }

    public ThirdParty createThirdParty(ThirdParty thirdParty){
        return thirdPartyRepository.save(thirdParty);
    }

    public boolean deleteThirdParty(Long id){
        if(thirdPartyRepository.existsById(id)){
            thirdPartyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<ThirdParty> updateThirdParty(Long id, ThirdParty thirdParty){
        if(thirdPartyRepository.existsById(id)){
            thirdParty.setId(id);
            return Optional.of(thirdPartyRepository.save(thirdParty));
        }
        return Optional.empty();
    }

}
