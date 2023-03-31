package com.skypro.bills.exception.handlers;

import com.skypro.bills.exception.ElectricityMeterNotFoundExeption;
import com.skypro.bills.exception.InvalidParametersExeption;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundMeterController {

    @ExceptionHandler(ElectricityMeterNotFoundExeption.class)
    public ResponseEntity<?> notFound() {
        return ResponseEntity.status(404).body("Такой не найден");
    }

    @ExceptionHandler(InvalidParametersExeption.class)
    public ResponseEntity<?> invalidParam() {
        return ResponseEntity.status(400).body("Неверно введены данные");
    }
}
