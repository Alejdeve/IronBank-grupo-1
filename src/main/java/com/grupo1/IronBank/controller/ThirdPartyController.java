package com.grupo1.IronBank.controller;

import com.grupo1.IronBank.model.ThirdParty;
import com.grupo1.IronBank.service.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/third-party")
public class ThirdPartyController {

    @Autowired
    private ThirdPartyService thirdPartyService;

    @GetMapping
    public List<ThirdParty> getAllThirdParties(){return thirdPartyService.getAllThirdParties();}

    @GetMapping("/{id}")
    public ResponseEntity<ThirdParty> getThirdPartyById(@PathVariable Long id){
        Optional<ThirdParty> thirdParty = thirdPartyService.getThirdPartyById(id);
        return thirdParty.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ThirdParty createThirdParty(@RequestBody ThirdParty thirdParty){
        return thirdPartyService.createThirdParty(thirdParty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThirdParty(@PathVariable Long id){
        if(thirdPartyService.deleteThirdParty(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThirdParty> updateThirdParty(@PathVariable Long id, @RequestBody ThirdParty thirdParty){
        Optional<ThirdParty> updatedThirdParty = thirdPartyService.updateThirdParty(id, thirdParty);
        return updatedThirdParty.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
}
