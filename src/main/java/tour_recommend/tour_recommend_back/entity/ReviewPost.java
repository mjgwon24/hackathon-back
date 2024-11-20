package tour_recommend.tour_recommend_back.entity;

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
@Table(name="review_post")
@Entity
public class ReviewPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int likeCount;

    @ElementCollection
    @CollectionTable(name = "review_post_images", joinColumns = @JoinColumn(name = "review_post_id"))
    private List<String> imagePathList;
}
