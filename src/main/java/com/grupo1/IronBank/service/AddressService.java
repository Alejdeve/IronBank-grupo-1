package com.grupo1.IronBank.service;

import com.grupo1.IronBank.classes.Address;
import com.grupo1.IronBank.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddress(){return addressRepository.findAll();}

    public Optional<Address> getAddressById(Long id){
        return addressRepository.findById(id);
    }

    public Address createAddress(Address address){
        return addressRepository.save(address);
    }

    public boolean deleteAddress(Long id){
        if(addressRepository.existsById(id)){
            addressRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Address> updateAddress(Long id, Address address){
        if(addressRepository.existsById(id)){
            address.setId(id);
            return Optional.of(addressRepository.save(address));
        }
        return Optional.empty();
    }

}
