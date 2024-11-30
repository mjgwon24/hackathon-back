package tour_recommend.tour_recommend_back.entity.estimate_talk;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import tour_recommend.tour_recommend_back.entity.sale.SalePost;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="estimate_talk_comment")
@Entity
public class EstimateTalkComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estimate_talk_comment_id")
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String author;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_talk_post_id", nullable = false)
    private EstimateTalkPost estimateTalkPost;
}