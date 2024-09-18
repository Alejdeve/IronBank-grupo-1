package com.grupo1.IronBank;

import com.grupo1.IronBank.classes.Address;
import com.grupo1.IronBank.classes.Money;
import com.grupo1.IronBank.model.*;
import com.grupo1.IronBank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private  ThirdPartyRepository thirdPartyRepository;


    @Override
    public void run(String... args) throws Exception{
        Address address1 = new Address();
        address1.setDirection("C/ Calamocha 4");
        address1.setCity("Valencia");
        address1.setPostalCode("46007");
        addressRepository.save(address1);

        Address address2 = new Address();
        address2.setDirection("C/ Ferrocarril 22");
        address2.setCity("Pinto");
        address2.setPostalCode("28320");
        addressRepository.save(address2);

        Address address3 = new Address();
        address3.setDirection("C/ Provencals 263");
        address3.setCity("Barcelona");
        address3.setPostalCode("08020");
        addressRepository.save(address3);

        Address address4 = new Address();
        address4.setDirection("C/ Cadiz 28");
        address4.setCity("Pinto");
        address4.setPostalCode("28320");
        addressRepository.save(address4);

        Address address5 = new Address();
        address5.setDirection("C/ Conca 1Bis");
        address5.setCity("Barcelona");
        address5.setPostalCode("08026");
        addressRepository.save(address5);

        AccountHolder accountHolder1 = new AccountHolder();
        accountHolder1.setName("Manuel R");
        accountHolder1.setBirthDate(LocalDate.parse("1966-08-01"));
        accountHolder1.setPrimaryAddress(address4);
        accountHolder1.setMailingAddress(address4);
        accountHolderRepository.save(accountHolder1);

        AccountHolder accountHolder2 = new AccountHolder();
        accountHolder2.setName("Francisco R");
        accountHolder2.setBirthDate(LocalDate.parse("2003-11-18"));
        accountHolder2.setPrimaryAddress(address3);
        accountHolder2.setMailingAddress(address4);
        accountHolderRepository.save(accountHolder2);

        AccountHolder accountHolder3 = new AccountHolder();
        accountHolder3.setName("Jon Manterola");
        accountHolder3.setBirthDate(LocalDate.parse("1994-09-16"));
        accountHolder3.setPrimaryAddress(address5);
        accountHolder3.setMailingAddress(address5);
        accountHolderRepository.save(accountHolder3);

        AccountHolder accountHolder4 = new AccountHolder();
        accountHolder4.setName("Alejandro G");
        accountHolder4.setBirthDate(LocalDate.parse("2005-11-18"));
        accountHolder4.setPrimaryAddress(address1);
        accountHolder4.setMailingAddress(address1);
        accountHolderRepository.save(accountHolder4);

        Admin admin = new Admin();
        admin.setName("admin");
        adminRepository.save(admin);

        Checking checking1 = new Checking();
        Money balance1  = new Money(new BigDecimal("24683.32"));
        Money minimumBalance1 = new Money(new BigDecimal("250"));
        Money monthlyFee1 = new Money(new BigDecimal("12"));
        checking1.setBalance(balance1);
        checking1.setSecretKey("1234abc");
        checking1.setPrimaryOwner(accountHolder1);
        checking1.setSecondaryOwner(null);
        checking1.setMinimumBalance(minimumBalance1);
        checking1.setMonthlyMaintenanceFee(monthlyFee1);
        checkingRepository.save(checking1);

        Checking checking2 = new Checking();
        Money balance2  = new Money(new BigDecimal("4277.98"));
        Money minimumBalance2 = new Money(new BigDecimal("250"));
        Money monthlyFee2 = new Money(new BigDecimal("12"));
        checking2.setBalance(balance2);
        checking2.setSecretKey("1234abc");
        checking2.setPrimaryOwner(accountHolder3);
        checking2.setSecondaryOwner(null);
        checking2.setMinimumBalance(minimumBalance2);
        checking2.setMonthlyMaintenanceFee(monthlyFee2);
        checkingRepository.save(checking2);

        StudentChecking checking3 = new StudentChecking();
        Money balance3  = new Money(new BigDecimal("8902.66"));
        Money minimumBalance3 = new Money(new BigDecimal("0"));
        Money monthlyFee3 = new Money(new BigDecimal("0"));
        checking3.setBalance(balance3);
        checking3.setSecretKey("1234abc");
        checking3.setPrimaryOwner(accountHolder2);
        checking3.setSecondaryOwner(accountHolder1);
        checking3.setMinimumBalance(minimumBalance3);
        checking3.setMonthlyMaintenanceFee(monthlyFee3);
        checkingRepository.save(checking3);

        StudentChecking checking4 = new StudentChecking();
        Money balance4  = new Money(new BigDecimal("17456.06"));
        Money minimumBalance4 = new Money(new BigDecimal("0"));
        Money monthlyFee4 = new Money(new BigDecimal("0"));
        checking4.setBalance(balance4);
        checking4.setSecretKey("1234abc");
        checking4.setPrimaryOwner(accountHolder4);
        checking4.setSecondaryOwner(null);
        checking4.setMinimumBalance(minimumBalance4);
        checking4.setMonthlyMaintenanceFee(monthlyFee4);
        checkingRepository.save(checking4);

        CreditCard creditCard1 = new CreditCard();
        Money balance5  = new Money(new BigDecimal("2873.06"));
        Money creditLimit1 = new Money(new BigDecimal("100"));
        creditCard1.setBalance(balance5);
        creditCard1.setSecretKey("1234abc");
        creditCard1.setPrimaryOwner(accountHolder1);
        creditCard1.setSecondaryOwner(null);
        creditCard1.setCreditLimit(creditLimit1);
        creditCard1.setInterestRate(new BigDecimal("0.2"));
        creditCardRepository.save(creditCard1);

        Savings savings1 = new Savings();
        Money balance6  = new Money(new BigDecimal("55467.06"));
        Money minimumBalance5 = new Money(new BigDecimal("1000"));
        savings1.setBalance(balance6);
        savings1.setSecretKey("1234abc");
        savings1.setPrimaryOwner(accountHolder3);
        savings1.setSecondaryOwner(null);
        savings1.setMinimumBalance(minimumBalance5);
        savings1.setInterestRate(new BigDecimal("0.0025"));
        savingsRepository.save(savings1);


        ThirdParty thirdparty = new ThirdParty();
        thirdparty.setName("thirdParty");
        thirdparty.setHashedKey("7c6IUH8Yftf5F");
        thirdPartyRepository.save(thirdparty);
    }
}
