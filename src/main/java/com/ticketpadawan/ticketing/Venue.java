package com.ticketpadawan.ticketing;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Venue implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static Venue instance = null;
  private static AtomicInteger heldSeatsCounter;
  private static AtomicInteger reservedSeatsCounter;
  private static AtomicInteger seatHoldId;
  private static int heldSeats;
  private static int reservedSeats;
  private static int totalSeats;
  private Section section;
  private int numRows;
  private int numSeatsPerRow;

  private Venue() {
    numRows = 10;
    numSeatsPerRow = 10;
    System.out.println("Creating a new venue;");
    totalSeats = numRows * numSeatsPerRow;
    heldSeatsCounter = new AtomicInteger(0);
    reservedSeatsCounter = new AtomicInteger(0);
    seatHoldId = new AtomicInteger(0);

    }

  
  public static Venue newInstance() {
    if (instance == null) {
      instance = new Venue();
    }
    return instance;
  }
  
  
  public synchronized int numSeatsAvailable() {
    return (totalSeats - heldSeats + reservedSeats);
  }


  public synchronized SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
    if (section == null)
      section = new Section(numRows, numSeatsPerRow);
    Seat[] seats = this.section.getSeatsForHold(numSeats);
    SeatHold hold = new SeatHold(seats, getNextSeatHoldId(), customerEmail);
    heldSeats = heldSeatsCounter.addAndGet(hold.numSeats());
    return hold;
  }


  public synchronized String reserveSeats(int seatHoldId, String customerEmail) {
    // TODO Auto-generated method stub
    return null;
  }
  

  private int getNextSeatHoldId() {
    return seatHoldId.incrementAndGet();
  }


}
