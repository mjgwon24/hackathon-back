package tour_recommend.tour_recommend_back.entity.campsite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="campsite_availability", uniqueConstraints = @UniqueConstraint(columnNames = {"campsite_id", "date"}))
@Entity
public class CampsiteAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    private Long id;

    private LocalDate date;

    @Column(nullable = false)
    private int availableCount;

    @Column(nullable = false)
    private int totalCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campsite_id")
    private Campsite campsite;
}
