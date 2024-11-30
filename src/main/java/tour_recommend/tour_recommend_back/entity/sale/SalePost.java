package tour_recommend.tour_recommend_back.entity.sale;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sale_post")
@Entity
public class SalePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_post_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;  // VEGETABLES, FRUITS, MEAT, MEAL_KIT

    @Column(nullable = false)
    private String shortDescription; // 재료 간단 설명

    @Column(nullable = false)
    private String detailedDescription; // 재료 상세 설명

    @Column(nullable = false)
    private Double price; // 가격

    @Column(nullable = false)
    private String sellerName; // 판매자 이름

    private String thumbnailPath; // 썸네일 이미지 경로

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Double rating;

    @ElementCollection
    @CollectionTable(name = "sale_post_images", joinColumns = @JoinColumn(name = "sale_post_id"))
    private List<String> imagePathList;
}
