package com.skypro.bills.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ElectricityMeter {
  @Id
  private String serialNumber;
  @OneToMany(mappedBy = "electricityMeter", cascade = CascadeType.ALL)
  private List<Indication> indications;
  private double balance;

  public double getBalance() {
    return balance;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public List<Indication> getIndications() {
    return indications;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }
}
