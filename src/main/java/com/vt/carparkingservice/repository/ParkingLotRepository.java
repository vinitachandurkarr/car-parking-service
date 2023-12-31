package com.vt.carparkingservice.repository;

import com.vt.carparkingservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParkingLotRepository extends JpaRepository<Car,Long> {
    Car findParkedCarById(Long carId);
    List<Car> findByParkingSpotIsNotNull();
}
