package com.vt.carparkingservice.dto;

import com.vt.carparkingservice.entity.ParkingSpot;
import lombok.Data;

@Data
public class CarDto {
    private Long carId;
    private String licensePlate;
    private ParkingSpot parkingSpot;
}
