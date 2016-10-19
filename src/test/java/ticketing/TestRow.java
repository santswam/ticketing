package ticketing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ticketpadawan.ticketing.Row;

public class TestRow {

  private Row row;

  @Before
  public void setUp() throws Exception {
    row = new Row(10);
  }

  @Test
  public void testRowCapacity() {
    assertEquals(row.getRemainingSeats(), 10);
  }

  @Test
  public void testRowCapacityAfterHold() {
    int seats[] = row.getSeats(3);
    assertEquals(row.getRemainingSeats(), 7);
    assertEquals(seats.length, 3);
   }


  
  

}
