package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.ReviewPost;

public interface ReviewPostRepository extends JpaRepository<ReviewPost, Long> {
}
