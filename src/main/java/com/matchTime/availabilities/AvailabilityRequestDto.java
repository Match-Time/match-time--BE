package com.matchTime.availabilities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class AvailabilityRequestDto {

    private List<TimeSlot> timeSlots;

    @Getter
    @NoArgsConstructor
    public static class TimeSlot {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate date;

        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        private LocalTime startTime;

        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        private LocalTime endTime;
    }
}
