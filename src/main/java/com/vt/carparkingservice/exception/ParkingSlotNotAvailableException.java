package com.vt.carparkingservice.exception;

public class ParkingSlotNotAvailableException extends RuntimeException{
        public ParkingSlotNotAvailableException(String message) {
            super(message);
        }
}
