package com.skypro.bills.service;

import com.skypro.bills.exception.ElectricityMeterNotFoundExeption;
import com.skypro.bills.exception.InvalidParametersExeption;
import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.model.Indication;
import com.skypro.bills.repository.MeterRepository;
import com.skypro.bills.repository.dto.BalanceDTO;
import com.skypro.bills.repository.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;

@Service
public class BillingService {

    @Value("${priceForKW}")
    private static double priceForKW;
    private final MeterRepository meterRepository;

    public BillingService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    private static double calculatingBalance(ElectricityMeter meter) {
        //Подсчитываем баланс - берем последние показания счетчика и умножаем на стоимость,
        // далее вычитаем из положенных на счетчик средств
        Indication indications = meter.getIndications().stream()
                .max(Comparator.comparing(Indication::getSendingDate))
                .orElse(new Indication());
        double spent = indications.getIndication() * priceForKW;
        return meter.getBalance() - spent;
    }

    public BalanceDTO pay(String serial, PaymentDTO paymentDTO) {
        ElectricityMeter meter = meterRepository.findById(serial).orElse(null);
        if (meter == null) throw new ElectricityMeterNotFoundExeption("Такой не найден");
        if (paymentDTO.getAmount() <= 0) {
            throw new InvalidParametersExeption("Сумма платежа меньше или равна 0");
        }
        meter.setBalance(meter.getBalance() + paymentDTO.getAmount());
        meterRepository.save(meter);
        return new BalanceDTO(serial, BillingService.calculatingBalance(meter));
    }

    public BalanceDTO pay(String serial) {
        ElectricityMeter meter = meterRepository.findById(serial).orElse(null);
        if (meter == null) throw new ElectricityMeterNotFoundExeption("Такой не найден");
        return new BalanceDTO(serial, BillingService.calculatingBalance(meter));
    }

}
