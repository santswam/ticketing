package com.ticketpadawan.ticketing;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ticketpadawan.ticketing.TicketEnquiry.STATUS;



public class Ticketing {

  public static void main(String args[]) throws InterruptedException, ExecutionException {
    
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    TicketEnquiry[] queue = new TicketEnquiry[100000];

    int min = 1;
    int max = 10;


    for (int i = 0; i < 2000; i++) {

      queue[i] =
          random(0, 1) == 1 ? new TicketEnquiry("test", random(min, max)) : new TicketEnquiry();
    }
    
    Set<Callable<TicketEnquiry>> callables = new HashSet<Callable<TicketEnquiry>>();
    for (int i = 0; i < 2000; i++) {
      callables.add(new TicketCounterQueue(queue[i]));
    
    }
    
    for (int i = 0; i < 2000; i++) {
      try{
      queue[i] = executorService.submit(new TicketCounterQueue(queue[i])).get();
        // if (queue[i].getStatus().equals(STATUS.HELD))
          System.out.println(queue[i]);
      if (queue[i].getStatus().equals(STATUS.HELD)) {
        TicketEnquiry reserve =
            executorService.submit(
                new TicketCounterQueue(new TicketEnquiry(queue[i].getSeatHoldId(), queue[i]
                    .getCustomerEmail()))).get();
          System.out.println(reserve.toString());
      }
      }
      catch (InterruptedException | ExecutionException e) {
        e.printStackTrace(System.err);
      }

    }
    executorService.shutdown();


  }

  private static int random(int m, int n) {
    return (int) (Math.random() * (n - m + 1)) + m;
  }
}
