package com.ticketpadawan.ticketing;

public class TicketCounter implements TicketService {

  private static Venue venue;

  public TicketCounter() {
    venue = Venue.newInstance();

  }


  public int numSeatsAvailable() {
    return venue.numSeatsAvailable();
  }

  public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
    return venue.findAndHoldSeats(numSeats, customerEmail);
  }

  public String reserveSeats(int seatHoldId, String customerEmail) {
    return venue.reserveSeats(seatHoldId, customerEmail);
  }
}
