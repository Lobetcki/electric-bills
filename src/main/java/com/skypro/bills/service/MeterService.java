package com.skypro.bills.service;

import com.skypro.bills.exception.ElectricityMeterNotFoundExeption;
import com.skypro.bills.exception.InvalidParametersExeption;
import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.model.Indication;
import com.skypro.bills.repository.MeterRepository;
import com.skypro.bills.repository.dto.MeterDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.UUID;

@Service
public class MeterService {
    private final MeterRepository meterRepository;

    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public MeterDTO createMeter(MeterDTO meterDTO) {
        return MeterDTO.fromMeterDTO(meterRepository.save(meterDTO.toMeter()));
    }

    private static Indication getMaxLastIndicator(ElectricityMeter meter) {
        if (meter == null) throw new ElectricityMeterNotFoundExeption("Такой не найден");
        return meter.getIndications().stream()
                .max(Comparator.comparing(Indication::getSendingDate))
                .orElse(new Indication());
    }

    public MeterDTO getMeter(String serialNumber) {
        ElectricityMeter meter = meterRepository.findById(serialNumber).orElse(null);
        if (meter == null) throw new ElectricityMeterNotFoundExeption("Такой " + serialNumber+ " не найден");
        MeterDTO meterDTO = MeterDTO.fromMeterDTO(meter);
        meterDTO.setLastIndication(MeterService.getMaxLastIndicator(meter).getIndication());
        return meterDTO;
    }

    public MeterDTO newIndication(String serialNumber, int intIndication) {

        ElectricityMeter meter = meterRepository.findById(serialNumber).orElse(null);
        if (meter == null) throw new ElectricityMeterNotFoundExeption("Такой " + serialNumber+ " не найден");
        if (intIndication < 0){
           throw new InvalidParametersExeption("Показания не могут быть отрицательными");
        }
        if (MeterService.getMaxLastIndicator(meter).getIndication() > intIndication) {
            throw new InvalidParametersExeption("Показания счетчика меньше предыдущих показаний");
        } else {
            Indication indication = new Indication();
            indication.setIndication(intIndication);
            indication.setId(UUID.randomUUID().toString());
            indication.setSendingDate(Instant.now());
            indication.setElectricityMeter(meter);
            meter.getIndications().add(indication);
            meterRepository.save(meter);
            return MeterDTO.fromMeterDTO(meter, indication);
        }
    }
}