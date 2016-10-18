package com.ticketpadawan.ticketing;

import java.io.Serializable;

public class SeatHold implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  

  private int seatHoldId;
  private String emailId;
  private Seat[] seats;
  private long timeStamp;

  public SeatHold(Seat[] seats, int seatHoldId, String emailId) {
    this.seatHoldId = seatHoldId;
    this.seats = seats;
    this.emailId = emailId;
  }

  public int getSeatHoldId() {
    return seatHoldId;
  }


  public String getEmailId() {
    return emailId;
  }


  public Seat[] getSeats() {
    return seats;
  }

  public int numSeats() {
    return seats.length;
  }

  /**
   * @return the timeStamp
   */
  public long getTimeStamp() {
    return timeStamp;
  }

  /**
   * @param timeStamp the timeStamp to set
   */
  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

}
