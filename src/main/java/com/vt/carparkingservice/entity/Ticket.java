package com.vt.carparkingservice.entity;

import lombok.Data;
import java.util.Date;
import java.util.UUID;
@Data
public class Ticket {
    private final String ticketId;
    private int spotNumber;
    private final String licensePlate;
    private final Date date;;
    public Ticket(int spotNumber, String licensePlate, Date date) {
        this.ticketId = generateTicketId();
        this.licensePlate = licensePlate;
        this.date = date;
    }

    private String generateTicketId() {
        // Using UUID to generate a unique ticket ID
        return UUID.randomUUID().toString().toUpperCase().substring(0, 8);
    }
}
