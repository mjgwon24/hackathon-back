package tour_recommend.tour_recommend_back.entity.estimate_talk;

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
@Table(name="estimate_talk_post")
@Entity
public class EstimateTalkPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estimate_talk_post_id")
    private Long id;

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
    @CollectionTable(name = "estimate_talk_post_images", joinColumns = @JoinColumn(name = "estimate_talk_post_id"))
    private List<String> imagePathList;
}
