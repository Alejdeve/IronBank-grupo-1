package com.grupo1.IronBank.model;

import com.grupo1.IronBank.classes.Money;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class CreditCard extends Account implements Serializable {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit_amount")),
            @AttributeOverride(name= "currency", column = @Column(name= "credit_limit_currency"))
    })
    private Money creditLimit;

    @Column(precision = 20, scale = 8)
    private BigDecimal interestRate;
    private LocalDate lastInterestAdded;

}
