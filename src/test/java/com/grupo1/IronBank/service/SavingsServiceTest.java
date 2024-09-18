package com.grupo1.IronBank.service;

import com.grupo1.IronBank.classes.Money;
import com.grupo1.IronBank.model.Savings;
import com.grupo1.IronBank.repository.SavingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SavingsServiceTest {

    @Mock
    private SavingsRepository savingsRepository;

    @InjectMocks
    private SavingsService savingsService;

    private Savings savings;

    @BeforeEach
    void setUp() {
        savings = new Savings();
        savings.setId(1L);
        savings.setBalance(new Money(BigDecimal.valueOf(1000)));
        savings.setMinimumBalance(new Money(BigDecimal.valueOf(1000)));
        savings.setInterestRate(BigDecimal.valueOf(0.0025));
        savings.setLastInterestAdded(null);
    }

    @Test
    void getAllSavings_ShouldReturnAllSavings() {
        List<Savings> savingsList = new ArrayList<>();
        savingsList.add(savings);

        when(savingsRepository.findAll()).thenReturn(savingsList);

        List<Savings> result = savingsService.getAllSavings();
        assertEquals(1, result.size());
        verify(savingsRepository, times(1)).findAll();
    }

    @Test
    void getSavingById_ShouldReturnSaving() {
        when(savingsRepository.findById(1L)).thenReturn(Optional.of(savings));

        Optional<Savings> result = savingsService.getSavingById(1L);
        assertTrue(result.isPresent());
        assertEquals(savings, result.get());
        verify(savingsRepository, times(1)).findById(1L);
    }

    @Test
    void deleteSaving_ShouldReturnTrue_WhenSavingExists() {
        when(savingsRepository.existsById(1L)).thenReturn(true);

        boolean result = savingsService.deleteSaving(1L);
        assertTrue(result);
        verify(savingsRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSaving_ShouldReturnFalse_WhenSavingDoesNotExist() {
        when(savingsRepository.existsById(1L)).thenReturn(false);

        boolean result = savingsService.deleteSaving(1L);
        assertFalse(result);
        verify(savingsRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void updateSavings_ShouldUpdateAndReturnSaving() {
        when(savingsRepository.existsById(1L)).thenReturn(true);
        when(savingsRepository.save(any(Savings.class))).thenReturn(savings);

        Optional<Savings> result = savingsService.updateSavings(1L, savings);
        assertTrue(result.isPresent());
        assertEquals(savings, result.get());
        verify(savingsRepository, times(1)).save(any(Savings.class));
    }

    @Test
    void applyInterest_ShouldApplyInterest_WhenOneYearHasPassed() {

        savings.setLastInterestAdded(LocalDate.now().minusYears(1).minusDays(1));
        savings.setInterestRate(BigDecimal.valueOf(0.0025));
        savings.setBalance(new Money(BigDecimal.valueOf(1000)));

        when(savingsRepository.save(any(Savings.class))).thenReturn(savings);

        savingsService.applyInterest(savings);

        BigDecimal expectedBalance = BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(1.0025));
        assertEquals(0, expectedBalance.compareTo(savings.getBalance().getAmount()));

        assertEquals(LocalDate.now(), savings.getLastInterestAdded());

        verify(savingsRepository, times(1)).save(savings);
    }

    @Test
    void applyInterest_ShouldNotApplyInterest_WhenLessThanOneYearHasPassed() {

        savings.setLastInterestAdded(LocalDate.now().minusMonths(6));
        savings.setInterestRate(BigDecimal.valueOf(0.0025));
        savings.setBalance(new Money(BigDecimal.valueOf(1000)));

        savingsService.applyInterest(savings);

        assertEquals(0, BigDecimal.valueOf(1000).compareTo(savings.getBalance().getAmount()));

        assertEquals(LocalDate.now().minusMonths(6), savings.getLastInterestAdded());

        verify(savingsRepository, times(0)).save(savings);
    }
}