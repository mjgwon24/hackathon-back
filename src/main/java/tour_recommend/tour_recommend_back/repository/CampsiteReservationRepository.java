package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.campsite.CampsiteReservation;

public interface CampsiteReservationRepository extends JpaRepository<CampsiteReservation, Long> {
}
