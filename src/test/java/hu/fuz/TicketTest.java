package hu.fuz;

import hu.fuz.ticketvalidator.Ticket;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TicketTest {

    final private String METRO_VALIDATION_CODE = "0643xxx911281305";
    final private String NOT_METRO_VALIDATION_CODE = "5422511281305";

    @Test
    public void isMetroLineTest(){
        Ticket ticket = new Ticket(METRO_VALIDATION_CODE);
        assertTrue(ticket.isMetroLine());
    }

    @Test
    public void isNotMetroLineTest(){
        Ticket ticket = new Ticket(NOT_METRO_VALIDATION_CODE);
        assertFalse(ticket.isMetroLine());
    }

}
