package com.grupo1.IronBank.repository;

import com.grupo1.IronBank.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
