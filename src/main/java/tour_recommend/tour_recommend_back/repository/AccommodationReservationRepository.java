package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.accommodation.AccommodationReservation;
import tour_recommend.tour_recommend_back.entity.sale.PurchaseHistory;

import java.util.List;

public interface AccommodationReservationRepository extends JpaRepository<AccommodationReservation, Long> {
    List<AccommodationReservation> findByPhoneNumberOrderByIdDesc(String phoneNumber);
}
