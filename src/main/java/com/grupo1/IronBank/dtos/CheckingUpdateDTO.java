package com.grupo1.IronBank.dtos;

import com.grupo1.IronBank.enums.Status;
import com.grupo1.IronBank.model.AccountHolder;
import com.grupo1.IronBank.model.ThirdParty;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CheckingUpdateDTO {

    private BigDecimal balance;
    private String secretKey;
    private AccountHolder primaryOwner;
    private AccountHolder secondaryOwner;
    private BigDecimal penaltyFee;
    private LocalDate creationDate;
    private Status status;
    private List<ThirdParty> thirdParty;
    private BigDecimal minimumBalance;
    private BigDecimal monthlyMaintenanceFee;
}
