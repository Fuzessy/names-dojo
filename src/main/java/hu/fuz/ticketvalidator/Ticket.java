package hu.fuz.ticketvalidator;

import java.util.regex.Pattern;

public class Ticket {

    /* from 5th character appers 'xxx' cahacters */
    private final String METRO_LINE_PATTERN = "^....xxx.*";

    private final String validationNumber;

    public Ticket(String validationNumber) {
        this.validationNumber = validationNumber;
    }

    public boolean isMetroLine() {
        Pattern pattern = Pattern.compile(METRO_LINE_PATTERN);
        return pattern.matcher(validationNumber).find();
    }
}
