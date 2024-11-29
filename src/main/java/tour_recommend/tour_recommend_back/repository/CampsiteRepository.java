package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.campsite.Campsite;

public interface CampsiteRepository extends JpaRepository<Campsite, Long> {
}
