package com.skypro.bills.exception;

public class ElectricityMeterNotFoundExeption extends RuntimeException {
    public ElectricityMeterNotFoundExeption(String message) {
        super(message);
    }
}
