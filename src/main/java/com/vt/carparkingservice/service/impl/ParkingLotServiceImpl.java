package com.vt.carparkingservice.service.impl;

import com.vt.carparkingservice.dto.ParkingLotDto;
import com.vt.carparkingservice.entity.Car;
import com.vt.carparkingservice.entity.ParkingLot;
import com.vt.carparkingservice.entity.ParkingSpot;
import com.vt.carparkingservice.entity.Ticket;
import com.vt.carparkingservice.exception.InvalidCarNumberException;
import com.vt.carparkingservice.exception.ParkingSlotNotAvailableException;
import com.vt.carparkingservice.repository.ParkingLotRepository;
import com.vt.carparkingservice.service.ParkingChargeStrategyService;
import com.vt.carparkingservice.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    private static ParkingLot parkingLot;
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    /**
     * if multiple threads try to access getParkingLot() concurrently, they will synchronize
     * their access and only one thread will initialize the ParkingLot instance while other threads
     * wait for it to finish.
     */
    public static ParkingLot getParkingLot() {
        if (parkingLot == null) {
            synchronized (ParkingLotDto.class) {
                if (parkingLot == null) {
                    return new ParkingLot();
                }
            }
        }
        return parkingLot;
    }
    public boolean initializeParkingSlots(int numberOfParkingSpots) {
        for (int i = 1; i <= numberOfParkingSpots; i++) {
            parkingLot.getParkingSpots().add(new ParkingSpot(i));
        }
        System.out.printf("Created a car parking lot with %s spots %n", numberOfParkingSpots);
        return true;
    }

    /**
     * it will park the car
     * @param car
     * @return Ticket
     * @throws ParkingSlotNotAvailableException
     */
    @Override
    public Ticket park(Car car) throws ParkingSlotNotAvailableException {
        ParkingSpot nextAvailableSpot = getNextAvailableSpot();

        nextAvailableSpot.occupySpot(car);
        System.out.printf("Allocated slot number: %d \n", nextAvailableSpot.getSpotNumber());
        return new Ticket(nextAvailableSpot.getSpotNumber(), car.getLicensePlate(), new Date());
    }

    private ParkingSpot getNextAvailableSpot() throws ParkingSlotNotAvailableException {
        for (ParkingSpot spot : parkingLot.getParkingSpots()) {
            if (spot.isEmpty()) {
                return spot;
            }
        }
        throw new ParkingSlotNotAvailableException("No Empty Spot available");
    }

    /**
     *
     * @return parking charges
     */
    @Override
    public int unPark(Ticket ticket, ParkingChargeStrategyService parkingCostStrategy) throws InvalidCarNumberException {
        int costByHours = 0;
        ParkingSpot parkingSpot;
        try {
            parkingSpot = getCarSpotByVehicleNumber(ticket.getLicensePlate());
            parkingSpot.vacateSpot();
            int hours = getHoursParked(ticket.getDate(), new Date());
            costByHours = parkingCostStrategy.getCharge(hours);
            System.out.println(
                    "Vehicle with registration " + ticket.getLicensePlate() + " at slot number " + parkingSpot.getSpotNumber()
                            + " was parked for " + hours + " hours and the total charge is " + costByHours);
        } catch (InvalidCarNumberException invalidCarNumber) {
            System.out.println(invalidCarNumber.getMessage());
            throw invalidCarNumber;
        }
        return costByHours;
    }

    /**
     * it will return details of parked car
     * @param carId
     * @return
     */
    @Override
    public Car getParkedCarDetails(Long carId) {
        return parkingLotRepository.findParkedCarById(carId);
    }

    /**
     *
     * @return all the parked cars
     */
    @Override
    public List<Car> getParkedCars() {
        return parkingLotRepository.findByParkingSpotIsNotNull();
    }
    private int getHoursParked(Date startDate, Date endDate) {
        long secs = (endDate.getTime() - startDate.getTime()) / 1000;
        return (int) (secs / 3600);
    }
    private ParkingSpot getCarSpotByVehicleNumber(String carNumber) throws InvalidCarNumberException {
        for (ParkingSpot parkingSpot : parkingLot.getParkingSpots()) {
            Car car = parkingSpot.getCar();
            if (car != null && car.getLicensePlate().equals(carNumber)) {
                return parkingSpot;
            }
        }
        throw new InvalidCarNumberException("Car with the registration number " + carNumber + " is not found");
    }
}
