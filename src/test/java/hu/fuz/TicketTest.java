package hu.fuz;

import hu.fuz.ticketvalidator.Ticket;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TicketTest {

    @Test
    public void isMetroLineTest(){
        String METRO_VALIDATION_CODE = "0643xxx911281305";
        Ticket ticket = new Ticket(METRO_VALIDATION_CODE);
        assertTrue(ticket.isMetroLine());
    }

    @Test
    public void isNotMetroLineTest(){
        String NOT_METRO_VALIDATION_CODE = "5422512041246";
        Ticket ticket = new Ticket(NOT_METRO_VALIDATION_CODE);
        assertFalse(ticket.isMetroLine());
    }

    @Test
    public void getMetroTravelStartDateTest(){
        String METRO_VALIDATION_CODE_START_20200924_1305 = "0643xxx009241305";
        Ticket ticket = new Ticket(METRO_VALIDATION_CODE_START_20200924_1305);
        assertEquals(LocalDateTime.of(2020,9,24,13,5), ticket.getTravelStartTime());
    }

    @Test
    public void getNotMetroTravelStartDateTest(){
        String METRO_VALIDATION_CODE_START_20200924_1305 = "5422512041246";
        Ticket ticket = new Ticket(METRO_VALIDATION_CODE_START_20200924_1305);
        assertEquals(LocalDateTime.of(2020,12,4,12,46), ticket.getTravelStartTime());
    }

}
