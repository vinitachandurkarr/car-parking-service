package com.vt.carparkingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    @Column
    private String licensePlate;
    /**
     * @Car and @ParkingSpot represents one-to-one relationship.
     * one @Car can be associated with only one @ParkingSpot at a time
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "car")
    private ParkingSpot parkingSpot;

    public Car(Long carId, String licensePlate) {
        this.carId = carId;
        this.licensePlate = licensePlate;
    }
}
