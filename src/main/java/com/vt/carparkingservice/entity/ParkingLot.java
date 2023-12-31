package com.vt.carparkingservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ParkingLot {
    @Id
    @Column(name = "parking_lot_id")
    private Long parkingLotId;
    @Column
    private String parkingName;
    @OneToMany(mappedBy="parkingLot")
    private List<ParkingSpot> parkingSpots;
}
