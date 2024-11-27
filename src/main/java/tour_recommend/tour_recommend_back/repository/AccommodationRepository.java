package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.accommodation.Accommodation;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {}
