package com.skypro.bills.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.model.Indication;

public class MeterDTO {

  private String serialNumber;
  @JsonProperty(access = Access.READ_ONLY)
  private int lastIndication;

  public static MeterDTO fromMeterDTO(ElectricityMeter meter){
    MeterDTO meterDTO = new MeterDTO();
    meterDTO.setSerialNumber(meter.getSerialNumber());
    return meterDTO;
  }

  public static MeterDTO fromMeterDTO(ElectricityMeter meter, Indication indication){
    MeterDTO meterDTO = new MeterDTO();
    meterDTO.setSerialNumber(meter.getSerialNumber());
    meterDTO.setLastIndication(indication.getIndication());
    return meterDTO;
  }

  public ElectricityMeter toMeter() {
    ElectricityMeter meter = new ElectricityMeter();
    meter.setSerialNumber(this.getSerialNumber());

    return meter;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public void setLastIndication(int lastIndication) {
    this.lastIndication = lastIndication;
  }
}
