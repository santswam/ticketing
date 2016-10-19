package com.ticketpadawan.ticketing;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.hashids.Hashids;

public class Venue implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static final String EMPTY_STRING = "";
  private static Venue instance = null;
  private static final int SEED_VALUE = 100000; // needed to generate a 5 digit reservation code
                                                // from the seatHoldID
  private static final String SALT = "my own salt"; // Salt for generating the reservation code
  private static AtomicInteger heldSeatsCounter;
  private static AtomicInteger reservedSeatsCounter;
  private static AtomicInteger seatHoldId;
  private static int heldSeats;
  private static int reservedSeats;
  private static int totalSeats;
  private Section section;
  private int numRows;
  private int numSeatsPerRow;
  private static final ConcurrentMap<Integer, SeatHold> holdQueue =
      new ConcurrentHashMap<Integer, SeatHold>();
  private static final ConcurrentMap<String, Seat[]> reserved =
      new ConcurrentHashMap<String, Seat[]>();

  private Hashids hashids;


  private Venue() {
    numRows = 1000;
    numSeatsPerRow = 50;
    totalSeats = numRows * numSeatsPerRow;
    heldSeatsCounter = new AtomicInteger(0);
    reservedSeatsCounter = new AtomicInteger(0);
    seatHoldId = new AtomicInteger(0);
    hashids = new Hashids(SALT);
    }

  
  public static Venue newInstance() {
    if (instance == null) {
      instance = new Venue();
    }
    return instance;
  }
  
  
  public synchronized int numSeatsAvailable() {
    return (totalSeats - heldSeats - reservedSeats);
  }


  public synchronized SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
    if (section == null)
      section = new Section(numRows, numSeatsPerRow);
    Seat[] seats = this.section.getSeatsForHold(numSeats);
    if (seats != null) {
    SeatHold hold = new SeatHold(seats, getNextSeatHoldId(), customerEmail);
    heldSeats = heldSeatsCounter.addAndGet(hold.numSeats());
    hold.setTimeStamp(getTimeStamp());
    holdQueue.putIfAbsent(hold.getSeatHoldId(), hold);

    return hold;
    } else {
      return null;
    }
  }


  public synchronized String reserveSeats(int seatHoldId, String customerEmail) {
    if (holdQueue.containsKey(seatHoldId)) {

      SeatHold hold = holdQueue.get(seatHoldId);
      if (customerEmail.equals(hold.getEmailId())) {

        String reservationCode = hashids.encode(SEED_VALUE + hold.getSeatHoldId()).toUpperCase();
        reserved.putIfAbsent(reservationCode, hold.getSeats());
        reservedSeats = reservedSeatsCounter.addAndGet(hold.numSeats());
        heldSeats = heldSeatsCounter.addAndGet(-1 * hold.numSeats());
        holdQueue.remove(seatHoldId);
        return reservationCode;
      }
    }
    return EMPTY_STRING;
  }
  

  private synchronized int getNextSeatHoldId() {
    return seatHoldId.incrementAndGet();
  }

  private synchronized long getTimeStamp() {
    return System.currentTimeMillis();
  }


}
