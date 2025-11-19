package com.matchTime.availabilities;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class MatchResultDto {

    private List<TopDate> topDates;
    private List<TimeSlot> topTimes;

    @Getter
    @Builder
    public static class TopDate {
        private LocalDate date;
        private long availableUserCount;
    }

    @Getter
    @Builder
    public static class TimeSlot {
        private LocalTime startTime;
        private LocalTime endTime;
        private long availableUserCount;
    }
}
