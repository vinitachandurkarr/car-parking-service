package com.vt.carparkingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpot {
    @Id
    @Column(name = "spot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spotId;
    @Column
    private int spotNumber;
    @Column
    private boolean isEmpty;
    /**
     * @Car and @ParkingSpot represents one-to-one relationship.
     * one parking spot can be occupied by only one car at a time
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        isEmpty = true;
    }
    public void vacateSpot() {
        car = null;
        this.isEmpty = true;
    }
    public void occupySpot(Car car) {
        this.car = car;
        this.isEmpty = false;
    }
}
