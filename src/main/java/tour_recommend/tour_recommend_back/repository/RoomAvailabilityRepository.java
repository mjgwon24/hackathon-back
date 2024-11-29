package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour_recommend.tour_recommend_back.entity.accommodation.RoomAvailability;

import java.time.LocalDate;

@Repository
public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {
    // 숙소 예약 시 해당 날짜의 available_count 감소
    @Modifying
    @Query("update RoomAvailability ra set ra.availableCount = ra.availableCount - 1 where ra.room.id = :roomId and ra.date = :date")
    void decreaseAvailableCountByRoomIdAndDate(@Param("roomId") Long roomId, @Param("date") LocalDate date);
}
