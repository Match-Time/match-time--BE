package com.matchTime.availabilities;

import com.matchTime.rooms.Room;
import com.matchTime.rooms.RoomRepository;
import com.matchTime.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeMatchingService {

    private final UserAvailabilityRepository availabilityRepository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public MatchResultDto match(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        List<UserAvailability> availabilities = availabilityRepository.findByRoom(room);

        List<MatchResultDto.TopDate> topDates = calculateTopDates(availabilities);
        List<MatchResultDto.TimeSlot> topTimes = calculateTopTimes(availabilities);

        return MatchResultDto.builder()
                .topDates(topDates)
                .topTimes(topTimes)
                .build();
    }

    private List<MatchResultDto.TopDate> calculateTopDates(List<UserAvailability> availabilities) {
        return availabilities.stream()
                .collect(Collectors.groupingBy(UserAvailability::getAvailableDate,
                        Collectors.mapping(UserAvailability::getUser, Collectors.toSet())))
                .entrySet().stream()
                .map(entry -> MatchResultDto.TopDate.builder()
                        .date(entry.getKey())
                        .availableUserCount(entry.getValue().size())
                        .build())
                .sorted((d1, d2) -> Long.compare(d2.getAvailableUserCount(), d1.getAvailableUserCount()))
                .limit(3)
                .collect(Collectors.toList());
    }

    private List<MatchResultDto.TimeSlot> calculateTopTimes(List<UserAvailability> availabilities) {
        final int intervalMinutes = 30;
        Map<LocalTime, Set<User>> intervalUserMap = new HashMap<>();

        LocalTime slot = LocalTime.of(0, 0);
        while (slot.isBefore(LocalTime.of(23, 59))) {
            intervalUserMap.put(slot, new HashSet<>());
            slot = slot.plusMinutes(intervalMinutes);
        }

        for (UserAvailability availability : availabilities) {
            LocalTime current = availability.getStartTime();
            while (current.isBefore(availability.getEndTime())) {
                LocalTime currentSlot = LocalTime.of(current.getHour(), (current.getMinute() / intervalMinutes) * intervalMinutes);
                if (intervalUserMap.containsKey(currentSlot)) {
                    intervalUserMap.get(currentSlot).add(availability.getUser());
                }
                current = current.plusMinutes(intervalMinutes);
            }
        }

        return intervalUserMap.entrySet().stream()
                .map(entry -> MatchResultDto.TimeSlot.builder()
                        .startTime(entry.getKey())
                        .endTime(entry.getKey().plusMinutes(intervalMinutes))
                        .availableUserCount(entry.getValue().size())
                        .build())
                .sorted((t1, t2) -> Long.compare(t2.getAvailableUserCount(), t1.getAvailableUserCount()))
                .limit(3)
                .collect(Collectors.toList());
    }
}
