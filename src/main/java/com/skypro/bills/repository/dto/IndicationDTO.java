package com.skypro.bills.repository.dto;

import com.skypro.bills.model.ElectricityMeter;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.Instant;


public class IndicationDTO {

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

  public void setElectricityMeter(ElectricityMeter electricityMeter) {
    this.electricityMeter = electricityMeter;
  }
}
