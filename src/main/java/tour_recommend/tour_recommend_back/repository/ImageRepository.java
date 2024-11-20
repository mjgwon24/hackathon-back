package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {}

