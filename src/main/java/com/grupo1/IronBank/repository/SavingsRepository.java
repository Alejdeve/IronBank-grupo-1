package com.grupo1.IronBank.repository;

import com.grupo1.IronBank.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
