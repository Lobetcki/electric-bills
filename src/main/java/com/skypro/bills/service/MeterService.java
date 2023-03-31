package com.skypro.bills.service;

import com.skypro.bills.exception.ElectricityMeterNotFoundExeption;
import com.skypro.bills.exception.InvalidParametersExeption;
import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.model.Indication;
import com.skypro.bills.repository.IndicationRepository;
import com.skypro.bills.repository.MeterRepository;
import com.skypro.bills.repository.dto.IndicationDTO;
import com.skypro.bills.repository.dto.MeterDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class MeterService {
    private final MeterRepository meterRepository;
    private final IndicationRepository indicationRepository;

    public MeterService(MeterRepository meterRepository, IndicationRepository indicationRepository) {
        this.meterRepository = meterRepository;
        this.indicationRepository = indicationRepository;
    }

    public MeterDTO createMeter(MeterDTO meterDTO) {
        return MeterDTO.fromMeterDTO(meterRepository.save(meterDTO.toMeter()));
    }

    public static void checkingForNull(ElectricityMeter meter) {
         if (meter == null) {
            throw new ElectricityMeterNotFoundExeption("Такой не найден");
        }
    }

    private static Indication maxLastIndicator(ElectricityMeter meter) {
        if (meter == null) throw new ElectricityMeterNotFoundExeption("Такой не найден");
        return meter.getIndications().stream()
                .max(Comparator.comparing(Indication::getSendingDate))
                .orElse(new Indication());
    }

    public MeterDTO getMeter(String serialNumber) {
        ElectricityMeter meter = meterRepository.findById(serialNumber).orElse(null); //.orElse(null);
        checkingForNull(meter);
            return MeterDTO.fromMeterDTO(meter, MeterService.maxLastIndicator(meter));
    }

    public MeterDTO newIndication(String serialNumber, int intIndication) {
        ElectricityMeter meter = meterRepository.findById(serialNumber).orElse(null);
        checkingForNull(meter);
        if (intIndication < 0){
           throw new InvalidParametersExeption("Показания не могут быть отрицательными");
        }
        if (MeterService.maxLastIndicator(meter).getIndication() > intIndication) {
            throw new InvalidParametersExeption("Показания счетчика меньше предыдущих показаний");
        } else {
            Indication indication = new Indication();
            indication.setIndication(intIndication);
            indication.setElectricityMeter(meter);
            IndicationDTO indicationDTO = IndicationDTO.fromMeterDTO(indication);
            indication = indicationDTO.toMeter();
            indicationRepository.save(indication);
            meter.getIndications().add(indication);
            meterRepository.save(meter);
            return MeterDTO.fromMeterDTO(meter, indication);
        }
    }
}