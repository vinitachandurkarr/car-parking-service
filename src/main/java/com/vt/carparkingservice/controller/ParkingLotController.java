package com.vt.carparkingservice.controller;

import com.vt.carparkingservice.entity.Car;
import com.vt.carparkingservice.entity.Ticket;
import com.vt.carparkingservice.service.ParkingChargeStrategyService;
import com.vt.carparkingservice.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingLotController {
    @Autowired
    private ParkingLotService parkingLotService;
    @PostMapping("/parkCar")
    public ResponseEntity<Ticket> parkCar(@RequestParam("car") Car car){
        Ticket parkingTicket = parkingLotService.park(car);
        if (parkingTicket!=null){
            return ResponseEntity.ok(parkingTicket); //Return Parking Ticket
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/unparkCar")
    public ResponseEntity<Integer> unparkCar(@RequestParam("ticket") Ticket ticket, @RequestParam("parking_charges") ParkingChargeStrategyService parkingCharge){
        String licensePlate = ticket.getLicensePlate();
        if(licensePlate!=null){
            int charges = parkingLotService.unPark(ticket, parkingCharge);
            return ResponseEntity.ok(charges);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/cars/{carId}")
    public ResponseEntity<Car> getParkedCarDetails(@PathVariable("carId") Long carId) {
        Car parkedCar = parkingLotService.getParkedCarDetails(carId);
        if (parkedCar != null) {
            return ResponseEntity.ok(parkedCar); // Return car details if found
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if car not found
        }
    }
    @GetMapping("/getParkedCars")
    public ResponseEntity<List<Car>> getParkedCars() {
        List<Car> parkedCars = parkingLotService.getParkedCars();
        if (parkedCars != null) {
            return ResponseEntity.ok(parkedCars); // Return car details if found
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if car not found
        }
    }
}
