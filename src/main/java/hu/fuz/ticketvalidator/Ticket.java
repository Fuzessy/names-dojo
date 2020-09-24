package hu.fuz.ticketvalidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Ticket {
    private static final String METRO_LINE_DATE_PATTERN = "yyyyMMddHHmm";
    private static final int NUMBER_OF_METRO_DATE_CHARACTERS = 9;
    private static final int NUMBER_OF_DATE_CHARACTERS = 8;
    /* from 5th character appers 'xxx' cahacters */
    private static final String METRO_LINE_PATTERN = "^....xxx.*";

    private final String validationNumber;

    public Ticket(String validationNumber) {
        this.validationNumber = validationNumber;
    }

    public boolean isMetroLine() {
        Pattern pattern = Pattern.compile(METRO_LINE_PATTERN);
        return pattern.matcher(validationNumber).find();
    }

    public LocalDateTime getTravelStartTime() {
        if(isMetroLine()){
            return determinateMetroTravelStartTime();
        }
        else{
            return determineTravelStartTime();
        }
    }

    private LocalDateTime determineTravelStartTime() {
        String dateCode = validationNumber.substring(validationNumber.length() - NUMBER_OF_DATE_CHARACTERS);
        dateCode = LocalDateTime.now().getYear() + dateCode;
        return LocalDateTime.parse(dateCode, DateTimeFormatter.ofPattern(METRO_LINE_DATE_PATTERN));
    }

    private LocalDateTime determinateMetroTravelStartTime() {
        String dateCode = validationNumber.substring(validationNumber.length() - NUMBER_OF_METRO_DATE_CHARACTERS);
        dateCode = "202" + dateCode;
        return LocalDateTime.parse(dateCode, DateTimeFormatter.ofPattern(METRO_LINE_DATE_PATTERN));
    }
}
