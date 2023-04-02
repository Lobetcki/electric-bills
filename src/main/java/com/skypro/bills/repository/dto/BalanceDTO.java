package com.skypro.bills.repository.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BalanceDTO {

  private String serialNumber;
  private double currentBalance;


  public static BalanceDTO fromMeterDTO(String serialNumber, double balance){
    BalanceDTO balanceDTO = new BalanceDTO();
    balanceDTO.setSerialNumber(serialNumber);
    balanceDTO.setCurrentBalance(balance);
    return balanceDTO;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public double getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(double currentBalance) {
    this.currentBalance = currentBalance;
  }
}
