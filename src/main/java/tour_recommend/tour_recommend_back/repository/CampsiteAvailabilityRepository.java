package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour_recommend.tour_recommend_back.entity.campsite.CampsiteAvailability;

import java.time.LocalDate;

@Repository
public interface CampsiteAvailabilityRepository extends JpaRepository<CampsiteAvailability, Long> {
    // 캠핑장 예약 시 해당 날짜의 available_count 감소
    @Modifying
    @Query("update CampsiteAvailability ca set ca.availableCount = ca.availableCount - 1 where ca.campsite.id = :campsiteId and ca.date = :date")
    void decreaseAvailableCountByCampsiteIdAndDate(@Param("campsiteId") Long campsiteId, @Param("date") LocalDate date);
}
