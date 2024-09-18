package com.grupo1.IronBank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo1.IronBank.classes.Money;
import com.grupo1.IronBank.model.CreditCard;
import com.grupo1.IronBank.repository.CreditCardRepository;
import com.grupo1.IronBank.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreditCardController.class)
public class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardService creditCardService;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @Test
    public void testGetAllCreditCards() throws Exception {
        CreditCard card1 = new CreditCard();
        CreditCard card2 = new CreditCard();
        Mockito.when(creditCardService.getAllCreditCards()).thenReturn(Arrays.asList(card1, card2));

        mockMvc.perform(get("/credit-card"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetCreditCardById_found() throws Exception {
        CreditCard card = new CreditCard();
        Mockito.when(creditCardService.getCreditCardById(1L)).thenReturn(Optional.of(card));

        mockMvc.perform(get("/credit-card/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCreditCardById_notFound() throws Exception {
        Mockito.when(creditCardService.getCreditCardById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/credit-card/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateCreditCard() throws Exception {
        CreditCard card = new CreditCard();
        card.setCreationDate(LocalDate.now());

        Mockito.when(creditCardService.createCreditCard(Mockito.any(CreditCard.class))).thenReturn(card);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String json = objectMapper.writeValueAsString(card);

        mockMvc.perform(post("/credit-card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCreditCard_success() throws Exception {
        Mockito.when(creditCardService.deleteCreditCard(1L)).thenReturn(true);

        mockMvc.perform(delete("/credit-card/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetBalance_success() throws Exception {
        CreditCard card = new CreditCard();
        card.setId(1L);
        card.setBalance(new Money(new BigDecimal("1000")));

        Mockito.when(creditCardService.getCreditCardById(1L)).thenReturn(Optional.of(card));

        mockMvc.perform(get("/credit-card/1/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1000));
    }

    @Test
    public void testGetBalance_notFound() throws Exception {
        Mockito.when(creditCardService.getCreditCardById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/credit-card/1/balance"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetBalance_withInterestApplied() throws Exception {

        CreditCard card = new CreditCard();
        card.setId(1L);
        card.setBalance(new Money(new BigDecimal("1000")));
        card.setInterestRate(new BigDecimal("0.12"));
        card.setLastInterestAdded(LocalDate.now().minusMonths(2));

        Mockito.when(creditCardService.getCreditCardById(1L)).thenReturn(Optional.of(card));

        Mockito.doAnswer(invocation -> {
            BigDecimal monthlyRate = new BigDecimal("0.12").divide(new BigDecimal("12"), 6, RoundingMode.DOWN);
            Money balance = card.getBalance();
            BigDecimal interest = balance.getAmount().multiply(monthlyRate);
            balance.setAmount(balance.getAmount().add(interest));
            card.setBalance(balance);
            card.setLastInterestAdded(LocalDate.now());
            return null;
        }).when(creditCardService).applyInterest(card);

        Mockito.when(creditCardRepository.save(Mockito.any(CreditCard.class))).thenReturn(card);

        mockMvc.perform(get("/credit-card/1/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1010.0));
    }
}