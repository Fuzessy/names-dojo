package hu.fuz.ticketvalidator;

import hu.fuz.TimeService;

import java.time.LocalDateTime;

public class FakeTimeService implements TimeService {
    private final LocalDateTime fakeCurrentTime;

    public FakeTimeService(LocalDateTime fakeCurrentTime) {
        this.fakeCurrentTime = fakeCurrentTime;
    }

    @Override
    public LocalDateTime getCurrentTime() {
        return fakeCurrentTime;
    }
}
