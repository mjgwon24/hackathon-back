package tour_recommend.tour_recommend_back.entity.sale;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour_recommend.tour_recommend_back.entity.accommodation.Accommodation;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="purchase_history")
@Entity
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_history_id")
    private Long id;

    @Column(nullable = false)
    private String category;  // VEGETABLES, FRUITS, MEAT, MEAL_KIT

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name; // 상품명

    @Column(nullable = false)
    private Double price; // 가격

    @Column(nullable = false)
    private Integer quantity; // 개수

    @Column(nullable = false)
    private Double totalPrice; // 총가격

    @Column(nullable = false)
    private String sellerName; // 판매자 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_post_id")
    private SalePost salePost;
}
