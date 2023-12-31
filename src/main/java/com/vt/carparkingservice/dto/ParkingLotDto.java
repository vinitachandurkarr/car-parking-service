package com.vt.carparkingservice.dto;

import com.vt.carparkingservice.entity.ParkingSpot;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Creating @ParkingLot class as Singleton to ensure that only one instance of a class will be created.
 */
@Data
public class ParkingLotDto {
    // Using volatile to ensure visibility across threads
    private static volatile ParkingLotDto parkingLot;
    private String parkingName;
    private List<ParkingSpot> parkingSpots;
    private ParkingLotDto() {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
    }

    /**
     * if multiple threads try to access getParkingLot() concurrently, they will synchronize
     * their access and only one thread will initialize the ParkingLot instance while other threads
     * wait for it to finish.
     */
    public static ParkingLotDto getParkingLot() {
        if (parkingLot == null) {
            synchronized (ParkingLotDto.class) {
                if (parkingLot == null) {
                    return new ParkingLotDto();
                }
            }
        }
        return parkingLot;
    }

}
