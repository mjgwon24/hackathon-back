package tour_recommend.tour_recommend_back.entity.campsite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="campsite")
@Entity
public class Campsite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campsite_id")
    private Long id;

    private String name;
    private String location;
    private String description;
    private Double price;
    private Double rating;
    private String thumbnailPath;

    @OneToMany(mappedBy = "campsite", fetch = FetchType.LAZY)
    private List<CampsiteAvailability> availabilities;

    @OneToMany(mappedBy = "campsite", fetch = FetchType.LAZY)
    private List<CampsiteReservation> reservations;
}
