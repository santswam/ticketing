# ticketing
Ticketing service for a high performance venue

Assumptions:

1. The best seats are the those that are closest to the stage (ideally centre of the row, but the current algorithm assigns seats from end-to-end. It is easy to change the seat assignment algorithm)
2. Empty single seats at the end of the row are avoided. When the request for a certain number of seats in a row and after assigning the seats, if only the aisle seat is remaining then the seats for the request is assigned from the next row. The reasoning being aisle single seats are tough to fill.(The edge case when it is the last row and a request leaves only one seat, then the seats are not held.)
3. The request for seats cannot exceed the the number of seats in a single row. 
4. The held seats are never released. (To add this feature, the hashmap has to be changed to an eviction enabled map)

Design Considerations:

* The lowest functional unit is a row. The Row Class maintains a linked list for the seats and removes the seat from the list whenever a seat is held. There is a method to add back seats if the hold expires(But the hold queue is not eviction based yet)
* A new unit called Section is used to hold the rows of seats. This will allow to create venues with multiple sections. The code currently handles only one section.
* The Section Object holds the active rows. Active rows are the rows which have empty seats. e.g. If the first row has 2 remaining seats and the request is for 4 seats, then the seats are assigned in the next row. The first row with two seats will be filled when the next request for 2 seats comes in.
* A section (or a group of section) makes up a Venue.
* The Venue object holds the concurrent maps for held and reserved seats as well the current count of seats held/reserved.
* TicketCounter class implements the TicketService interface.
* TicketEnquiry is a common object used for exchanging information, i.e. it has the information about the type of enquiry (availability, hold or reserve) the relevant values & as well the response values.
* The Venue capacity is currently  hard coded to hold 2000 seats with 100 rows and 20 seats per row.This can be changed by modifying the values of 
numRows & numSeatsPerRow variables in the Venue class.

* When the program is run, it queues up the requests and collects the responses and prints them some sample responses are

REQUEST = HOLD::Status = HELD:: Seat Hold ID = 1:: Seats requested=5::Seats=[1-1-0, 1-1-1, 1-1-2, 1-1-3, 1-1-4]

	The above output is for 
        . A request type hold.
        . The status is the seats have been held.  
	. The seat hold ID is 1.
        . seats requested is 5 
	. the seats are 0-4 on the first row of section 1 (only one section)

REQUEST = AVAILABILITY::Status = SUCCESS::Available Seats=10
  
    The above output is for 
	. a request type availability
	. The status is success indicating the request has been responded to.
        . Available seats are 10.


REQUEST = RESERVE::Status = RESERVED::SeatHoldId= 372 Reservation Code= DBNAP

	The above output is for
	. a request type reserve
	. The status is reserved.
        . The seat hold id is 372	
	. reservation code is DBNAP

   
 
* Hashids library is used to generate 5 character string from seatHoldID after seeding.

* TO run the program, do a maven clean install and run with the main class com.ticketpadawan.ticketing.Ticketing

java -cp target/ticketing-0.0.1-SNAPSHOT.jar com.ticketpadawan.ticketing.Ticketing





	
