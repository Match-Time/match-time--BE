package com.matchTime.availabilities;

import com.matchTime.rooms.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserAvailabilityRepository extends JpaRepository<UserAvailability, Long> {
    List<UserAvailability> findByRoom(Room room);
}
