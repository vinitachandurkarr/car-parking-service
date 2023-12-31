package com.vt.carparkingservice.dto;

import com.vt.carparkingservice.entity.Car;
import lombok.Data;

@Data
public class ParkingSpotDto {
    private Long spotId;
    private int spotNumber;
    private boolean isEmpty;
    private Car car;
    public void vacateSlot() {
        car = null;
        this.isEmpty = true;
    }
    public void occupySlot(Car car) {
        this.car = car;
        this.isEmpty = false;
    }
}
