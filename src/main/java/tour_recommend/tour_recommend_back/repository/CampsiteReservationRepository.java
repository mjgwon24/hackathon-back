package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.accommodation.AccommodationReservation;
import tour_recommend.tour_recommend_back.entity.campsite.CampsiteReservation;

import java.util.List;

public interface CampsiteReservationRepository extends JpaRepository<CampsiteReservation, Long> {
    List<CampsiteReservation> findByPhoneNumberOrderByIdDesc(String phoneNumber);

}
