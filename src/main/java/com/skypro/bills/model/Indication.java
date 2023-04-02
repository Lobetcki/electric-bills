package com.skypro.bills.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Indication {

  @Id
  private String id;
  private int indication;
  private Instant sendingDate;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private ElectricityMeter electricityMeter;

  public void setId(String id) {
    this.id = id;
  }

  public int getIndication() {
    return indication;
  }

  public void setIndication(int indication) {
    this.indication = indication;
  }

  public Instant getSendingDate() {
    return sendingDate;
  }

  public void setSendingDate(Instant sendingDate) {
    this.sendingDate = sendingDate;
  }

  public ElectricityMeter getElectricityMeter() {
    return electricityMeter;
  }

  public void setElectricityMeter(ElectricityMeter electricityMeter) {
    this.electricityMeter = electricityMeter;
  }
}
