package com.skypro.bills.controller;

import com.skypro.bills.repository.dto.BalanceDTO;
import com.skypro.bills.repository.dto.PaymentDTO;
import com.skypro.bills.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
//TODO: Хорошо бы задокументировать АПИ :-(

public class BillingController {

  //TODO: Стоит сделать это свойство конфигурируемым через application.properties

  private final BillingService billingService;

  public BillingController(BillingService billingService) {
    this.billingService = billingService;
  }

  @PostMapping("/{serial}")
  public ResponseEntity<?> pay(@PathVariable("serial") String serial,
      @RequestBody PaymentDTO paymentDTO) {
    BalanceDTO balanceDTO = billingService.pay(serial, paymentDTO);
    return ResponseEntity.ok(balanceDTO);
  }

  @GetMapping("/{serial}")
  public ResponseEntity<?> pay(@PathVariable("serial") String serial) {
    BalanceDTO balanceDTO = billingService.pay(serial, null);
    return ResponseEntity.ok(balanceDTO);
  }
}
