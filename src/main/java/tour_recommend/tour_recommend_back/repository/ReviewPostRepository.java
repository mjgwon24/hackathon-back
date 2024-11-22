package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.ReviewPost;

import java.util.List;

public interface ReviewPostRepository extends JpaRepository<ReviewPost, Long> {
    // 모든 후기 조회 - createdAt 기준 최신순 반환
    List<ReviewPost> findAllByOrderByCreatedAtDesc();
}
