package com.matchTime.rooms;

import com.matchTime.roomUsers.RoomUserRepository;
import com.matchTime.users.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomUserRepository roomUserRepository;

    @Transactional
    public Long createRoom(RoomCreateRequestDto requestDto) {
        return roomRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public RoomResponseDto findById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다. id=" + id));
        return new RoomResponseDto(room);
    }

    @Transactional(readOnly = true)
    public List<RoomResponseDto> findByTitle(String title) {
        return roomRepository.findAllByTitleContaining(title).stream()
                .map(RoomResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsersInRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다. id=" + roomId));

        return roomUserRepository.findByRoom(room).stream()
                .map(roomUser -> new UserResponseDto(roomUser.getUser()))
                .collect(Collectors.toList());
    }
}
