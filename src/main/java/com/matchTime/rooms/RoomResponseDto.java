package com.matchTime.rooms;

import lombok.Getter;

@Getter
public class RoomResponseDto {

    private final Long id;
    private final String title;

    public RoomResponseDto(Room room) {
        this.id = room.getId();
        this.title = room.getTitle();
    }
}
