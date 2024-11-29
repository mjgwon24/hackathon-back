package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.accommodation.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
