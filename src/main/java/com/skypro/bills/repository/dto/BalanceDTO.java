package com.skypro.bills.repository.dto;

public class BalanceDTO {

  private String serialNumber;
  private double currentBalance;

  public BalanceDTO(String serialNumber, double currentBalance) {
    this.serialNumber = serialNumber;
    this.currentBalance = currentBalance;
  }
}
