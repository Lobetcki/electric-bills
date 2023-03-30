package com.skypro.bills.service;

import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.model.Indication;
import com.skypro.bills.repository.MeterRepository;
import com.skypro.bills.repository.dto.MeterDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;

@Service
public class MeterService {
    MeterRepository meterRepository;

    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public MeterDTO createMeter(MeterDTO meterDTO) {
        MeterDTO meterDTO1 = new MeterDTO();

        ElectricityMeter meter = new ElectricityMeter();

        meter.setSerialNumber(meterDTO.getSerialNumber());

        meter = meterRepository.save(meter);

        meterDTO1.setSerialNumber(meter.getSerialNumber());

        return meterDTO1;
    }


    public MeterDTO getMeter(@PathVariable("serial") String serialNumber) {

        MeterDTO meterDTO = new MeterDTO();

        meterDTO.setSerialNumber(meterRepository.findById(serialNumber).get().getSerialNumber());

        meterDTO.setLastIndication(

                meterRepository.findById(serialNumber).get().getIndications().stream()

                        .max(Comparator.comparing(Indication::getSendingDate))

                        .orElse(new Indication()).getIndication());

        return meterDTO;
    }
}