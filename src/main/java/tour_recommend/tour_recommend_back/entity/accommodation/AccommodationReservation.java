package tour_recommend.tour_recommend_back.entity.accommodation;

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
@Table(name="accommodation_reservation")
@Entity
public class AccommodationReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_reservation_id")
    private Long id;

    private String phoneNumber;

    private String email;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;

    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id") // 방에 대한 참조 추가
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
}