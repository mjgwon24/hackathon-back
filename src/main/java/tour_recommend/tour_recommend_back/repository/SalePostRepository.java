package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.sale.SalePost;

public interface SalePostRepository extends JpaRepository<SalePost, Long> {
    Page<SalePost> findByCategory(String category, PageRequest pageRequest);
}
