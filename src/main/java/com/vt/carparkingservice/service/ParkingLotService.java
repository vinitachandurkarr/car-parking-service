package com.vt.carparkingservice.service;

import com.vt.carparkingservice.entity.Car;
import com.vt.carparkingservice.entity.Ticket;

import java.util.List;

public interface ParkingLotService {
    public Ticket park(Car car);
    public int unPark(Ticket ticket, ParkingChargeStrategyService parkingCostStrategy);
    Car getParkedCarDetails(Long carId);
    List<Car> getParkedCars();
}
