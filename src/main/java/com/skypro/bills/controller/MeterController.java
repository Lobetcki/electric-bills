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
    return ResponseEntity.ok(meterService.getMeter(serialNumber));
  }

  @PostMapping("/{serial}/{indication}")

  public ResponseEntity<MeterDTO> newIndication(@PathVariable("serial") String serial,
      @PathVariable("indication") int indication) {
      return ResponseEntity.ok(meterService.newIndication(serial, indication));
    }
}
