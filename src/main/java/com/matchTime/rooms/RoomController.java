package com.matchTime.rooms;

import com.matchTime.availabilities.MatchResultDto;
import com.matchTime.availabilities.TimeMatchingService;
import com.matchTime.users.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final TimeMatchingService timeMatchingService;

    @PostMapping
    public Long createRoom(@RequestBody RoomCreateRequestDto requestDto) {
        return roomService.createRoom(requestDto);
    }

    @GetMapping("/{id}")
    public RoomResponseDto findById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @GetMapping
    public List<RoomResponseDto> findByTitle(@RequestParam String title) {
        return roomService.findByTitle(title);
    }

    @GetMapping("/{id}/match")
    public MatchResultDto match(@PathVariable Long id) {
        return timeMatchingService.match(id);
    }

    @GetMapping("/{id}/users")
    public List<UserResponseDto> getUsersInRoom(@PathVariable Long id) {
        return roomService.getUsersInRoom(id);
    }
}
