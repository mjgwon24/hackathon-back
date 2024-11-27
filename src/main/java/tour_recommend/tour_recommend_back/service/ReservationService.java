package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationResponse.FetchedReservation;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationResponse.FetchedRoom;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationResponse;
import tour_recommend.tour_recommend_back.entity.accommodation.Accommodation;
import tour_recommend.tour_recommend_back.repository.AccommodationRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final AccommodationRepository accommodationRepository;

    public FetchAccommodationResponse fetchAccommodation(Long accommodationId) {
        Accommodation fetchedAccommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new RuntimeException("accommodationId에 해당하는 숙소가 존재하지 않습니다."));

        List<FetchedRoom> rooms = fetchedAccommodation.getRooms().stream()
                .map(room -> FetchedRoom.builder()
                        .id(room.getId())
                        .roomType(room.getRoomType())
                        .build())
                .toList();

        List<FetchedReservation> reservations = fetchedAccommodation.getReservations().stream()
                .map(reservation -> FetchedReservation.builder()
                        .id(reservation.getId())
                        .phoneNumber(reservation.getPhoneNumber())
                        .checkInDate(reservation.getCheckInDate())
                        .checkOutDate(reservation.getCheckOutDate())
                        .totalPrice(reservation.getTotalPrice())
                        .build())
                .toList();

        return FetchAccommodationResponse.builder()
                .id(fetchedAccommodation.getId())
                .name(fetchedAccommodation.getName())
                .location(fetchedAccommodation.getLocation())
                .description(fetchedAccommodation.getDescription())
                .price(fetchedAccommodation.getPrice())
                .rooms(rooms)
                .reservations(reservations)
                .build();
    }
}
