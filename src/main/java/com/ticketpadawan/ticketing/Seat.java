package com.ticketpadawan.ticketing;

import java.io.Serializable;

public class Seat implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  int rowNum;
  int seatNum;
  int sectionNum;

  public Seat(int seatNum, int rowNum, int sectionNum) {
    this.rowNum = rowNum;
    this.seatNum = seatNum;
    this.sectionNum = sectionNum;
  }

  @Override
  public String toString() {
    return sectionNum + "-" + rowNum + "-" + seatNum;
  }

}
