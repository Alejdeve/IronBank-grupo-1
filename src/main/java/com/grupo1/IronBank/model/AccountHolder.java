package com.grupo1.IronBank.model;

import com.grupo1.IronBank.classes.Address;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class AccountHolder extends User implements Serializable {

    private LocalDate birthDate;

    @ManyToOne
    private Address primaryAddress;

    @ManyToOne
    private Address mailingAddress;

}
