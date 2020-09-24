package hu.fuz.ticketvalidator;

import hu.fuz.TimeService;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TicketValidatorTest {

    private final static int METRO_VALIDITY_PERIOD_IN_MINUTES = 120;

    private final String METRO_VALIDATION_CODE_START_AT_5_0809_1011 = "0643xxx508091011";
    private LocalDateTime timeOf202508091011 = LocalDateTime.of(2025,8,9,10,11,0);
    TimeService timeService;
    TicketValidator validator;
    Ticket ticket;

    @Test
    public void checkValidMetroLineTicketTest(){
        setUpTestAndSetCurrentTime202508091011plusMinutes(1);
        assertTrue(validator.isMetroTicketValid(ticket));
    }

    private void setUpTestAndSetCurrentTime202508091011plusMinutes(int extraMinutes) {
        timeService = new FakeTimeService(timeOf202508091011.plusMinutes(extraMinutes));
        validator = new TicketValidator(timeService);
        ticket = new Ticket(METRO_VALIDATION_CODE_START_AT_5_0809_1011, timeService);
    }

    @Test
    public void checkExpiredMetroLineTicket(){
        setUpTestAndSetCurrentTime202508091011plusMinutes(METRO_VALIDITY_PERIOD_IN_MINUTES + 1);
        assertFalse(validator.isMetroTicketValid(ticket));
    }

    @Test
    public void checkValidMetroLineTicketOnTheEdge(){
        setUpTestAndSetCurrentTime202508091011plusMinutes(METRO_VALIDITY_PERIOD_IN_MINUTES);
        assertTrue(validator.isMetroTicketValid(ticket));
    }
}
