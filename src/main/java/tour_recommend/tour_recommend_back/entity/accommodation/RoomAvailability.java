package tour_recommend.tour_recommend_back.entity.accommodation;

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
@Table(name = "room_availability", uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "date"})) // 날짜와 방은 유일해야 함
@Entity
public class RoomAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    private Long id;

    private LocalDate date; // 특정 날짜

    @Column(nullable = false)
    private int availableCount; // 해당 날짜에 남아있는 방의 수(예약되지 않은 방)

    @Column(nullable = false)
    private int totalCount; // 해당 날짜에 총 방의 수(예약된 방 포함)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}