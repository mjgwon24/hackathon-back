package tour_recommend.tour_recommend_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import tour_recommend.tour_recommend_back.entity.sns_auth.SnsAuthPost;

public interface SnsAuthPostRepository extends JpaRepository<SnsAuthPost, Long> {
    Page<SnsAuthPost> findByPostType(String postType, PageRequest pageRequest);
}
