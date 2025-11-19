package com.matchTime.availabilities;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms/{roomId}/users/{userId}/availability")
public class UserAvailabilityController {

    private final UserAvailabilityService availabilityService;

    @PostMapping
    public ResponseEntity<Void> saveAvailabilities(@PathVariable Long userId,
                                                   @PathVariable Long roomId,
                                                   @RequestBody AvailabilityRequestDto requestDto) {
        availabilityService.saveAvailabilities(userId, roomId, requestDto);
        return ResponseEntity.ok().build();
    }
}
