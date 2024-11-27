package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.image.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {}

