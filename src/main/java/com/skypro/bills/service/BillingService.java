package com.skypro.bills.service;

import com.skypro.bills.exception.InvalidParametersExeption;
import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.projections.CurrentBalance;
import com.skypro.bills.repository.MeterRepository;
import com.skypro.bills.repository.dto.BalanceDTO;
import com.skypro.bills.repository.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingService {

    @Value("${priceForKW}")
    private double priceForKW;
    private final MeterRepository meterRepository;

    public BillingService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public BalanceDTO pay(String serial, PaymentDTO paymentDTO) {
        //Подсчитываем баланс - берем последние показания счетчика и умножаем на стоимость,
        // далее вычитаем из положенных на счетчик средств
        ElectricityMeter meter = meterRepository.findById(serial).orElse(null);
        MeterService.checkingForNull(meter);
        if (paymentDTO != null) {
            if (paymentDTO.getAmount() <= 0) {
                throw new InvalidParametersExeption("Сумма платежа меньше или равна 0");
            }
        meter.setSerialNumber(serial);
        meter.setBalance(meter.getBalance() + paymentDTO.getAmount());
        meterRepository.save(meter);
        }
        CurrentBalance currentBalance = meterRepository.payBalance(serial);

        double spent = currentBalance.getIndication() * priceForKW;
        double balance = currentBalance.getBalance() - spent;

        return BalanceDTO.fromMeterDTO(serial, balance);
    }
}
