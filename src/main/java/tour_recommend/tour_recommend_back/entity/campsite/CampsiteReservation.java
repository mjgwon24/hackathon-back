package tour_recommend.tour_recommend_back.entity.campsite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="campsite_reservation")
@Entity
public class CampsiteReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campsite_reservation_id")
    private Long id;

    private String phoneNumber;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campsite_id")
    private Campsite campsite;
}
