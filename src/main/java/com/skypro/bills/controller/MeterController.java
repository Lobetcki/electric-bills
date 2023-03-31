package com.skypro.bills.controller;

import com.skypro.bills.repository.dto.MeterDTO;
import com.skypro.bills.service.MeterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meter")
//TODO: Хорошо бы задокументировать АПИ :-(

public class MeterController {

  private final MeterService meterService;

  public MeterController(MeterService meterService) {
    this.meterService = meterService;
  }

  @PostMapping
  public ResponseEntity<MeterDTO> createMeter(@RequestBody MeterDTO meterDTO) {
    MeterDTO meterDTO1 = meterService.createMeter(meterDTO);
    return ResponseEntity.ok(meterDTO1);
  }

  @GetMapping("/{serial}")
  public ResponseEntity<MeterDTO> getMeter(@PathVariable("serial") String serialNumber) {
    MeterDTO meterDTO = meterService.getMeter(serialNumber);
    return ResponseEntity.ok(meterDTO);
  }

  @PostMapping("/{serial}/{indication}")

  public ResponseEntity<MeterDTO> newIndication(@PathVariable("serial") String serial,
      @PathVariable("indication") int indication) {
    MeterDTO meterDTO = meterService.newIndication(serial, indication);

//    if (indication < 0){
//      return ResponseEntity.badRequest().body("Показания не могут быть отрицательными");
//    }
//
//    if (lastIndication.getIndication() > indication) {
//      return ResponseEntity.badRequest().body("Показания счетчика меньше предыдущих показаний");
//    } else {

      return ResponseEntity.ok(meterDTO);
    }
}
