package tour_recommend.tour_recommend_back.dto.accommodation;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record accommodationDto() {
    // 숙소 생성 요청
    @Builder
    public record FetchAccommodationResponse(
            Long id,
            String name,
            String location,
            String description,
            Double price,
            List<FetchedRoom> rooms,
            List<FetchedReservation> reservations
    ) {
        @Builder
        public record FetchedRoom(
                Long id,
                String roomType
        ) {}
        @Builder
        public record FetchedReservation(
                Long id,
                String phoneNumber,
                LocalDateTime checkInDate,
                LocalDateTime checkOutDate,
                Double totalPrice
        ) {}
    }

    // 숙소 목록 조회 응답
    @Builder
    public record FetchAccommodationsResponse(
            List<FetchedAccommodation> accommodations,
            int currentPage,
            int totalPages,
            Long totalElements
    ) {
        @Builder
        public record FetchedAccommodation(
                Long id,
                String name,
                String location,
                String description,
                Double price,
                Double rating,
                String thumbnailPath
        ) {}
    }
}
