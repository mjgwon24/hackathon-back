package tour_recommend.tour_recommend_back.entity.sns_auth;

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
@Table(name="sns_auth_post")
@Entity
public class SnsAuthPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sns_auth_post_id")
    private Long id;

    // 숙소 방문 인증(ACCOMMODATION_VISIT) OR 쓰레기 처리 인증(WASTE_DISPOSAL)
    @Column(nullable = false)
    private String postType;

    @Column(nullable = false)
    private String snsUserName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "sns_auth_post_images", joinColumns = @JoinColumn(name = "sns_auth_post_id"))
    private List<String> imagePathList;
}
