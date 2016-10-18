package com.ticketpadawan.ticketing;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.hashids.Hashids;

public class TicketCounter implements TicketService {

  private static Venue venue;
  private static final String EMPTY_STRING = "";
  private static final ConcurrentMap<Integer, SeatHold> holdQueue =
      new ConcurrentHashMap<Integer, SeatHold>();
  private static final ConcurrentMap<String, Seat[]> reserved =
      new ConcurrentHashMap<String, Seat[]>();

  private Hashids hashids;

  public TicketCounter() {
    venue = Venue.newInstance();
    hashids = new Hashids("salt");
  }


  public int numSeatsAvailable() {
    return venue.numSeatsAvailable();
  }

  public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
    SeatHold hold = venue.findAndHoldSeats(numSeats, customerEmail);
    hold.setTimeStamp(getTimeStamp());
    holdQueue.putIfAbsent(hold.getSeatHoldId(), hold);
    return hold;
  }

  public String reserveSeats(int seatHoldId, String customerEmail) {
    if (holdQueue.containsKey(seatHoldId)) {
      SeatHold hold = holdQueue.get(seatHoldId);
      if (customerEmail.equals(hold.getEmailId())) {

        String reservationCode = hashids.encode(1000000 + hold.getSeatHoldId()).toUpperCase();
        reserved.putIfAbsent(reservationCode, hold.getSeats());
        holdQueue.remove(seatHoldId);
        return reservationCode;
      }
    }
      return EMPTY_STRING;
  }

  private long getTimeStamp() {
    return System.currentTimeMillis();
  }
}
