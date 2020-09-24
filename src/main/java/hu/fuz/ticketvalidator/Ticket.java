package hu.fuz.ticketvalidator;

import hu.fuz.TimeService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Ticket {
    private static final String DATE_TIME_PATTERN = "yyyyMMddHHmm";
    private static final int NUMBER_OF_METRO_DATE_CHARACTERS = 9;
    private static final int NUMBER_OF_DATE_CHARACTERS = 8;
    /* from 5th character appers 'xxx' cahacters */
    private static final String METRO_LINE_PATTERN = "^....xxx.*";

    private final String validationNumber;
    private final TimeService timeService;

    public Ticket(String validationNumber, TimeService timeService) {
        this.validationNumber = validationNumber;
        this.timeService = timeService;
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
        String dateCodeFromTicket = validationNumber.substring(validationNumber.length() - NUMBER_OF_DATE_CHARACTERS);
        return determineTravelStartTimeByDateCode(dateCodeFromTicket);
    }

    private LocalDateTime determineTravelStartTimeByDateCode(String dateCodeFromTicket) {
        String dateCode = LocalDateTime.now().getYear() + dateCodeFromTicket;
        LocalDateTime parsedDate = LocalDateTime.parse(dateCode, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        if(parsedDate.isAfter(timeService.getCurrentTime())){
            parsedDate = fixStartDateTime(dateCodeFromTicket);
        }
        return parsedDate;
    }

    private LocalDateTime fixStartDateTime(String dateCodeFromTicket) {
        String dateCode = (timeService.getCurrentTime().getYear()-1) + dateCodeFromTicket;
        return LocalDateTime.parse(dateCode, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    private LocalDateTime determinateMetroTravelStartTime() {
        String dateCodeFromTicket = validationNumber.substring(validationNumber.length() - NUMBER_OF_METRO_DATE_CHARACTERS);
        String dateCode = getMetroISODateCode(dateCodeFromTicket, timeService.getCurrentTime().getYear());
        LocalDateTime parsedDate = LocalDateTime.parse(dateCode, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        if(parsedDate.isAfter(timeService.getCurrentTime())){
            parsedDate = fixMetroStartDateTime(dateCodeFromTicket);
        }
        return parsedDate;
    }

    private String getMetroISODateCode(String dateCodeFromTicket, int year) {
        return (year + "").substring(0, 3) + dateCodeFromTicket;
    }

    private LocalDateTime fixMetroStartDateTime(String dateCodeFromTicket) {
        String dateCode = getMetroISODateCode(dateCodeFromTicket, timeService.getCurrentTime().getYear()-10);
        return LocalDateTime.parse(dateCode, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }
}
