package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.SalePost;

import java.util.List;

public interface SalePostRepository extends JpaRepository<SalePost, Long> {
    // 카테고리별 후기 조회 - createdAt 기준 최신순 반환
    List<SalePost> findAllByCategoryOrderByCreatedAtDesc(String category);
    Page<SalePost> findByCategory(String category, PageRequest pageRequest);
}
