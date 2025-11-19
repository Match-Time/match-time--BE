package com.matchTime.roomUsers;

import com.matchTime.rooms.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    List<RoomUser> findByRoom(Room room);
}
