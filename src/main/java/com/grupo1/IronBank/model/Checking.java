package com.grupo1.IronBank.model;

import com.grupo1.IronBank.classes.Money;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Checking extends Account implements Serializable {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount")),
            @AttributeOverride(name= "currency", column = @Column(name= "minimum_balance_currency"))
    })
    private Money minimumBalance;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount")),
            @AttributeOverride(name= "currency", column = @Column(name= "monthly_maintenance_fee_currency"))
    })
    private Money monthlyMaintenanceFee;
}
