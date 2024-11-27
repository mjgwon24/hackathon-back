package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.post.SnsAuthPost;

public interface SnsAuthPostRepository extends JpaRepository<SnsAuthPost, Long> {}
