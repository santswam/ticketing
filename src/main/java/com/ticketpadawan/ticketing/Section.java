package com.ticketpadawan.ticketing;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class Section implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int numRows;
  private int numSeatsPerRow;
  private Deque<Row> activeRows;
  private static int sectionCounter;
  private int sectionNum;
  private int rowCounter = 0;

  public Section(int numRows,int numSeatsPerRow) {

    sectionCounter++;
    this.sectionNum=sectionCounter;
    this.numRows = numRows;
    this.numSeatsPerRow = numSeatsPerRow;
    activeRows = new ArrayDeque<Row>();
    activeRows.add(getNewRow(numSeatsPerRow));
  }

  private synchronized Row getNewRow(int numSeatsPerRow) {
    rowCounter++;
    return new Row(numSeatsPerRow);
  }


  public synchronized Seat[] getSeatsForHold(int numSeats) {
    boolean seatsAlloted = false;
    int[] seatPos = new int[numSeats];
    int rowNum=0;
    for (Iterator<Row> itr = activeRows.iterator(); itr.hasNext();) {
      Row r = itr.next();
      int remainingSeats = r.getRemainingSeats();
      if (remainingSeats >= numSeats & remainingSeats - 1 != numSeats) {
        seatPos = r.getSeats(numSeats);
        rowNum = r.getRowNum();
        seatsAlloted = true;
        break;
      }
      else {
        if (r.getRemainingSeats() == 0) {
          itr.remove();
        }
      }
    }
    if (!seatsAlloted && rowCounter < numRows) {
      Row newRow = getNewRow(numSeatsPerRow);
      seatPos = newRow.getSeats(numSeats);
      rowNum = newRow.getRowNum();
      activeRows.addLast(newRow);
      seatsAlloted = true;
    }

    return seatsAlloted ? getSeatsforPos(seatPos, rowNum) : null;
  }



private synchronized Seat[] getSeatsforPos(int[] position, int rowNum) {
  Seat[] seats = new Seat[position.length];
  for(int i=0;i<position.length;i++){
      seats[i] = new Seat(position[i], rowNum,sectionNum);
    }

    return seats;
}

  public synchronized int getSectionNum() {
    return this.sectionNum;
  }
}