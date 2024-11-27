package tour_recommend.tour_recommend_back.entity.accommodation;

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
@Table(name="room")
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String roomType; // 예: 싱글, 더블, 스위트 등

    // roomAvailability 사용시 -> totalCount, availableCount 필요 X
//    private int totalCount; // 총 방 개수
//    private int availableCount; // 잔여 방 개수

    @OneToMany(mappedBy = "room")
    private List<RoomAvailability> availabilities; // 방의 날짜별 가용성

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
}