package com.skypro.bills.repository;

import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.projections.CurrentBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterRepository extends JpaRepository<ElectricityMeter, String> {

    @Query(value = "SELECT e.balance AS balance, MAX(i.indication) AS indication\n" +
            "FROM electricity_meter e\n" +
            "JOIN indication i\n" +
            "    ON e.serial_number = i.electricity_meter_serial_number\n" +
            "WHERE i.electricity_meter_serial_number=?1\n" +
            "GROUP BY i.electricity_meter_serial_number, e.balance", nativeQuery = true)
    CurrentBalance payBalance(String serialNumber);
}
