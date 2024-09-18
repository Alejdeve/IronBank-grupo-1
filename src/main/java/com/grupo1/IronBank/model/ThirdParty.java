package com.grupo1.IronBank.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class ThirdParty extends User implements Serializable {

    private String hashedKey;
}
