package com.skypro.bills.repository.dto;

import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.model.Indication;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.time.Instant;
import java.util.UUID;


public class IndicationDTO {

  private String id;
  private int indication;
  private Instant sendingDate;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private ElectricityMeter electricityMeter;

  public static IndicationDTO fromMeterDTO(Indication indication){
    IndicationDTO indicationDTO = new IndicationDTO();
    indicationDTO.setIndication(indication.getIndication());
    indicationDTO.setId(UUID.randomUUID().toString());
    indicationDTO.setSendingDate(Instant.now());
    indicationDTO.setElectricityMeter(indication.getElectricityMeter());
    return indicationDTO;
  }

  public Indication toMeter() {
    Indication indication = new Indication();
    indication.setIndication(this.getIndication());
    indication.setId(this.getId());
    indication.setSendingDate(this.getSendingDate());
    indication.setElectricityMeter(this.getElectricityMeter());
    return indication;
  }

  public String getId() {
    return id;
  }

  public ElectricityMeter getElectricityMeter() {
    return electricityMeter;
  }

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
