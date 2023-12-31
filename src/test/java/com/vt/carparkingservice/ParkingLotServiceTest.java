package com.vt.carparkingservice;

import com.vt.carparkingservice.entity.Car;
import com.vt.carparkingservice.entity.ParkingSpot;
import com.vt.carparkingservice.entity.Ticket;
import com.vt.carparkingservice.exception.ParkingSlotNotAvailableException;
import com.vt.carparkingservice.repository.ParkingLotRepository;
import com.vt.carparkingservice.service.ParkingChargeStrategyService;
import com.vt.carparkingservice.service.ParkingLotService;
import com.vt.carparkingservice.service.impl.ParkingLotServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParkingLotServiceTest {
    private ParkingLotServiceImpl parkingLotService;
    private ParkingLotRepository parkingLotRepository;
    private ParkingChargeStrategyService parkingCostStrategy;

    @Before
    public void setup() {
        parkingLotService = new ParkingLotServiceImpl();
        parkingLotRepository = mock(ParkingLotRepository.class);
        parkingCostStrategy = mock(ParkingChargeStrategyService.class);
        parkingLotService.initializeParkingSlots(20); //Initialize with 20 slots
    }
    @Test
    public void testPark() throws ParkingSlotNotAvailableException {
        Car testCar = new Car(123L, "ABCD123");
        Ticket ticket = parkingLotService.park(testCar);

        assertNotNull(ticket);
    }

    @Test(expected = ParkingSlotNotAvailableException.class)
    public void testPark_NoAvailableSpot() throws ParkingSlotNotAvailableException {
        for (ParkingSpot spot : parkingLotService.getParkingLot().getParkingSpots()) {
            spot.occupySpot(new Car(456L, "XYZ456L"));
        }
        Car testCar = new Car(4586L, "XYZ456LP");
        parkingLotService.park(testCar);
    }

    @Test
    public void testGetParkedCars() {
        List<Car> parkedCars = new ArrayList<>();
        parkedCars.add(new Car(9886L, "4586LJH"));
        parkedCars.add(new Car(98586L, "JHGF86L"));
        when(parkingLotRepository.findByParkingSpotIsNotNull()).thenReturn(parkedCars);

        List<Car> result = parkingLotService.getParkedCars();
        assertEquals(2, result.size());
    }
}

