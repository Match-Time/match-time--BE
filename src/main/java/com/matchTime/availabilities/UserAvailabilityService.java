package com.matchTime.availabilities;

import com.matchTime.rooms.Room;
import com.matchTime.rooms.RoomRepository;
import com.matchTime.users.User;
import com.matchTime.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAvailabilityService {

    private final UserAvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public void saveAvailabilities(Long userId, Long roomId, AvailabilityRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

        List<UserAvailability> availabilities = requestDto.getTimeSlots().stream()
                .map(slot -> UserAvailability.builder()
                        .user(user)
                        .room(room)
                        .availableDate(slot.getDate())
                        .startTime(slot.getStartTime())
                        .endTime(slot.getEndTime())
                        .build())
                .collect(Collectors.toList());

        availabilityRepository.saveAll(availabilities);
    }
}
