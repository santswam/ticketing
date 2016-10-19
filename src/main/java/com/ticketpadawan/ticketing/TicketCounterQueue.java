package com.ticketpadawan.ticketing;

import java.util.concurrent.Callable;

import com.ticketpadawan.ticketing.TicketEnquiry.STATUS;

public class TicketCounterQueue implements Callable<TicketEnquiry> {

  public static enum TRANSACTION {
    AVAILABILITY, HOLD, RESERVE
  };


  private TicketEnquiry request;
  private TicketCounter counter;

  public TicketCounterQueue(TicketEnquiry request) {
    
    this.request = request;
    request.setTimestamp(getTimeStamp());
    counter = new TicketCounter();

  }

  public TicketEnquiry call() throws Exception {
    switch (request.getTransType()) {
      case AVAILABILITY:
        updateResponseWithAvailability();
        break;
      case HOLD:
        updateResponseWithHoldInfo();
        break;
      case RESERVE:
        updateResponseWithReserveInfo();
        break;
      default:
        break;

    }

    return this.request;
  }
  
  
  
  private void updateResponseWithHoldInfo() {
    
    if (counter.numSeatsAvailable() >= request.getNumSeats()) {
    SeatHold hold = counter.findAndHoldSeats(request.getNumSeats(), request.getCustomerEmail());
      if (hold != null) {
        request.setSeatHoldId(hold.getSeatHoldId());
        request.setSeats(hold.getSeats());
        request.setStatus(STATUS.HELD);
      } else {
        request.setStatus(STATUS.UNAVAILABLE);
      }
    } else {
      request.setStatus(STATUS.UNAVAILABLE);
    }
  }

  private void updateResponseWithAvailability() {

    request.setNumSeatsAvailable(counter.numSeatsAvailable());
    request.setStatus(STATUS.SUCCESS);

  }

  private void updateResponseWithReserveInfo() {

     String reservationCode = counter.reserveSeats(request.getSeatHoldId(), request.getCustomerEmail());
    if (reservationCode.length() > 0) {
      request.setReservationCode(reservationCode);
      request.setStatus(STATUS.RESERVED);
    } else {
      request.setStatus(STATUS.TIMEOUT);
    }
  }

  private long getTimeStamp() {
    return System.currentTimeMillis();
  }

}

