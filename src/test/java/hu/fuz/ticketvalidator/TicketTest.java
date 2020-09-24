package hu.fuz.ticketvalidator;

import hu.fuz.TimeService;
import hu.fuz.ticketvalidator.Ticket;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TicketTest {

    @Test
    public void isMetroLineTest(){
        String METRO_VALIDATION_CODE = "0643xxx911281305";
        Ticket ticket = new Ticket(METRO_VALIDATION_CODE, null);
        assertTrue(ticket.isMetroLine());
    }

    @Test
    public void isNotMetroLineTest(){
        String NOT_METRO_VALIDATION_CODE = "5422512041246";
        Ticket ticket = new Ticket(NOT_METRO_VALIDATION_CODE, null);
        assertFalse(ticket.isMetroLine());
    }

    @Test
    public void getMetroTravelStartDateTest(){
        String METRO_VALIDATION_CODE_START_20200924_1305 = "0643xxx009241305";
        Ticket ticket = new Ticket(METRO_VALIDATION_CODE_START_20200924_1305, new FakeTimeService(LocalDateTime.of(2020,9,24,13,50)));
        assertEquals(LocalDateTime.of(2020,9,24,13,5), ticket.getTravelStartTime());
    }

    @Test
    public void getNotMetroTravelStartDateTest(){
        String METRO_VALIDATION_CODE_START_20200924_1305 = "5422512041246";
        Ticket ticket = new Ticket(METRO_VALIDATION_CODE_START_20200924_1305, new FakeTimeService(LocalDateTime.of(2020,12,4,12,50)));
        assertEquals(LocalDateTime.of(2020,12,4,12,46), ticket.getTravelStartTime());
    }

    @Test
    public void getTravelStartDateAt2021WhenTicketNumperPrintedLastYearTest(){
        String METRO_VALIDATION_CODE_START_20200924_1305 = "5422512041246";
        TimeService timeService = new FakeTimeService(LocalDateTime.of(2021,9,24,0,0,0));
        Ticket ticket = new Ticket(METRO_VALIDATION_CODE_START_20200924_1305, timeService);
        assertEquals(LocalDateTime.of(2020,12,4,12,46), ticket.getTravelStartTime());
    }

    @Test
    public void getTravelStartDateAt2021WhenTicketNumperPrintedCurrentYearTest(){
        String METRO_VALIDATION_CODE_START_20200924_1305 = "5422512041246";
        TimeService timeService = new FakeTimeService(LocalDateTime.of(2021,12,4,12,46,1));
        Ticket ticket = new Ticket(METRO_VALIDATION_CODE_START_20200924_1305, timeService);
        assertEquals(LocalDateTime.of(2020,12,4,12,46), ticket.getTravelStartTime());
    }

    @Test
    public void getMetroTravelStartDateAt2030AndTicketPrintedInTheCurrentXearTest(){
        String METRO_VALIDATION_CODE_START_20200924_1305 = "0643xxx009241305";
        TimeService timeService = new FakeTimeService(LocalDateTime.of(2030,9,24,13,5,1));
        Ticket ticket = new Ticket(METRO_VALIDATION_CODE_START_20200924_1305, timeService);
        assertEquals(LocalDateTime.of(2030,9,24,13,5), ticket.getTravelStartTime());
    }

    @Test
    public void getMetroTravelStartDateAt2030WhenTicketNumperPrintedLastDecadeTest(){
        String METRO_VALIDATION_CODE_START_20200924_1305 = "0643xxx009241305";
        TimeService timeService = new FakeTimeService(LocalDateTime.of(2030,9,24,13,04,1));
        Ticket ticket = new Ticket(METRO_VALIDATION_CODE_START_20200924_1305, timeService);
        assertEquals(LocalDateTime.of(2020,9,24,13,5), ticket.getTravelStartTime());
    }

    private class FakeTimeService implements TimeService {
        private final LocalDateTime fakeCurrentTime;

        public FakeTimeService(LocalDateTime fakeCurrentTime) {
            this.fakeCurrentTime = fakeCurrentTime;
        }

        @Override
        public LocalDateTime getCurrentTime() {
            return fakeCurrentTime;
        }
    }
}
