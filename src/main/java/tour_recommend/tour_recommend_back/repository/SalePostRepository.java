package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.SalePost;

import java.util.List;

public interface SalePostRepository extends JpaRepository<SalePost, Long> {
    // 모든 후기 조회 - createdAt 기준 최신순 반환
    List<SalePost> findAllByOrderByCreatedAtDesc();

    // 카테고리별 후기 조회 - createdAt 기준 최신순 반환
    List<SalePost> findAllByCategoryOrderByCreatedAtDesc(String category);
}
