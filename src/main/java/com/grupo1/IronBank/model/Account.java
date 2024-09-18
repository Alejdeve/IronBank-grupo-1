package com.grupo1.IronBank.model;

import com.grupo1.IronBank.classes.Money;
import com.grupo1.IronBank.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@MappedSuperclass
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount")),
            @AttributeOverride(name= "currency", column = @Column(name= "balance_currency"))
    })
    private Money balance;
    private String secretKey;

    @ManyToOne
    private AccountHolder primaryOwner;

    @ManyToOne
    private AccountHolder secondaryOwner;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount")),
            @AttributeOverride(name= "currency", column = @Column(name= "penalty_fee_currency"))
    })
    private Money penaltyFee;
    private LocalDate creationDate;
    private Status status;

    @ManyToMany
    private List<ThirdParty> thirdParty;

    public Account() {
        this.penaltyFee = new Money(new BigDecimal(40));
        this.status = Status.ACTIVE;
        this.creationDate = LocalDate.now();
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = new Money(new BigDecimal(40));
    }

}
