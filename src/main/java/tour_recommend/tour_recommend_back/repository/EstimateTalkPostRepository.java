package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.estimate_talk.EstimateTalkPost;

public interface EstimateTalkPostRepository extends JpaRepository<EstimateTalkPost, Long> {
}
