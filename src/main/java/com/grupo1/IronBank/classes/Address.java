package com.grupo1.IronBank.classes;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Embeddable
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direction;
    private String city;
    private String postalCode;
}
