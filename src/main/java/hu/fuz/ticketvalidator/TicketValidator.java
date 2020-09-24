package hu.fuz.ticketvalidator;

import hu.fuz.TimeService;

public class TicketValidator {
    private final TimeService timeService;
    private final static int METRO_VALIDITY_PERIOD_IN_MINUTES = 80;

    public TicketValidator(TimeService timeService) {
        this.timeService = timeService;
    }

    public boolean isMetroTicketValid(Ticket ticket) {
        return timeService.getCurrentTime().isBefore(
                ticket.getTravelStartTime().plusMinutes(METRO_VALIDITY_PERIOD_IN_MINUTES+1));
    }
}
