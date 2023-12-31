package com.vt.carparkingservice.service.impl;

import com.vt.carparkingservice.service.ParkingChargeStrategyService;
import org.springframework.stereotype.Service;

@Service
public class ParkingChargeStrategyImpl implements ParkingChargeStrategyService {
    @Override
    public int getCharge(int parkHours) {
        if (parkHours < 1) {
            return 10;
        }
        return parkHours * 10;
    }
}
