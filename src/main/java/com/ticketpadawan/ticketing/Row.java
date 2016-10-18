package com.ticketpadawan.ticketing;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.commons.lang3.ArrayUtils;

public class Row implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  int numSeats;
  int rowNum;
  static int counter = 0;
  LinkedList<Integer> seats;

  public Row(int numSeats) {
    counter++;
    rowNum = counter;
    seats = new LinkedList<Integer>();
    this.numSeats = numSeats;
    for (int i = 0; i < numSeats; i++) {
      seats.add(i);
    }
  }

  public synchronized int getRowNum() {
    return rowNum;
  }


  public synchronized int getRemainingSeats() {
    return seats.size();
  }

  public synchronized int[] getSeats(int numSeats) {
    int[] retSeats = new int[numSeats];
    for (int i = 0; i < numSeats; i++) {
      retSeats[i] = seats.getFirst();
      seats.removeFirst();
    }
    return retSeats;
  }

  public synchronized void addSeats(int[] releasedSeats) {
    int maxSeatIndex = releasedSeats.length;
    int maxSeatNum = releasedSeats[maxSeatIndex];
    ListIterator<Integer> iterator = seats.listIterator(0);
    int insertIndex = 0;
    while (iterator.hasNext()) {
      if ((Integer) iterator.next() > maxSeatNum)
        insertIndex++;
      else
        break;
    }
    seats.addAll(insertIndex - 1, Arrays.asList(ArrayUtils.toObject(releasedSeats)));
  }

  @Override
  public synchronized String toString() {
    return "Row number=" + this.rowNum + ": Available seats=" + seats.size();
  }

}
