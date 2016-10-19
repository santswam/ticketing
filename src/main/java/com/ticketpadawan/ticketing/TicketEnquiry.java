package com.ticketpadawan.ticketing;

import java.util.Arrays;

import com.ticketpadawan.ticketing.TicketCounterQueue.TRANSACTION;

public class TicketEnquiry {

  private String customerEmail;
  private int numSeats;
  private int numSeatsAvailable;
  private int seatHoldId;
  private TRANSACTION transType;
  private boolean isReserved;
  private Seat[] seats;
  private STATUS status;
  private long timestamp;
  private String reservationCode;

  public static enum STATUS {
    UNAVAILABLE, HELD, RESERVED, SUCCESS, TIMEOUT
  };


  public TicketEnquiry() {
    transType = TRANSACTION.AVAILABILITY;
  }
  
  public TicketEnquiry(String customerEmail, int numSeats) {
    this.customerEmail = customerEmail;
    transType = TRANSACTION.HOLD;
    this.numSeats = numSeats;
  }

  public TicketEnquiry(int seatHoldId, String customerEmail) {
    this.seatHoldId = seatHoldId;
    this.customerEmail = customerEmail;
    transType = TRANSACTION.RESERVE;
  }


  /**
   * @return the customerEmail.
   */

  public String getCustomerEmail() {
    return customerEmail;
  }


  /**
   * @return the numSeats.
   */
  public int getNumSeats() {
    return numSeats;
  }
  

  /**
   * @return the seatHoldId
   */
  public int getSeatHoldId() {
    return seatHoldId;
  }

  /**
   * @return the transType
   */
  public TRANSACTION getTransType() {
    return transType;
  }

  public void setTransType(TRANSACTION transType) {
    this.transType = transType;
  }

  /**
   * @return the isReserved
   */
  public boolean isReserved() {
    return isReserved;
  }

  /**
   * @param isReserved the isReserved to set
   */
  public void setReserved(boolean isReserved) {
    this.isReserved = isReserved;
  }

  /**
   * @return the seats
   */
  public Seat[] getSeats() {
    return seats;
  }

  /**
   * @param seats the seats to set
   */
  public void setSeats(Seat[] seats) {
    this.seats = seats;
  }

  /**
   * @return the numSeatsAvailable
   */
  public int getNumSeatsAvailable() {
    return numSeatsAvailable;
  }

  /**
   * @param numSeatsAvailable the numSeatsAvailable to set
   */
  public void setNumSeatsAvailable(int numSeatsAvailable) {
    this.numSeatsAvailable = numSeatsAvailable;
  }

  /**
   * @param numSeatsAvailable the numSeatsAvailable to set
   */
  public void setSeatHoldId(int seatHoldId) {
    this.seatHoldId = seatHoldId;
  }



  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("REQUEST = " + transType.toString() + "::");
    sb.append("Status = " + status.toString() + "::");
    switch (transType) {
      case AVAILABILITY:
        sb.append("Available Seats=" + numSeatsAvailable);
        break;
      case HOLD:
        sb.append((status ==
 STATUS.HELD ? " Seat Hold ID = "
 + seatHoldId
 + ":: Seats requested=" + numSeats
 + "::Seats=" + Arrays.toString(seats) : ":: Seats requested=" + numSeats));
        break;
      case RESERVE:
        sb.append("SeatHoldId= " + seatHoldId + " Reservation Code= " + reservationCode);
        break;
      default:
        break;

    }
    return sb.toString();
  }

  /**
   * @return the status
   */
  public STATUS getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(STATUS status) {
    this.status = status;
  }

  /**
   * @return the timestamp
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * @param timestamp the timestamp to set
   */
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * @return the reservationCode
   */
  public String getReservationCode() {
    return reservationCode;
  }

  /**
   * @param reservationCode the reservationCode to set
   */
  public void setReservationCode(String reservationCode) {
    this.reservationCode = reservationCode;
  }
}
