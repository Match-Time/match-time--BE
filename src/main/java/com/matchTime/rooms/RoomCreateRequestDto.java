package com.matchTime.rooms;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomCreateRequestDto {

    @NotBlank
    private String title;

    public Room toEntity() {
        return Room.builder()
                .title(title)
                .build();
    }
}
