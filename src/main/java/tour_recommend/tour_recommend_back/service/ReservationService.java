package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationsResponse;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationsResponse.FetchedAccommodation;
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
                .rating(fetchedAccommodation.getRating())
                .thumbnailPath(fetchedAccommodation.getThumbnailPath())
                .rooms(rooms)
                .reservations(reservations)
                .build();
    }

    // 숙소 목록 조회
    public FetchAccommodationsResponse fetchAccommodations(int pageNumber, int size) {
        // Sort 객체 생성하여 정렬 기준 설정
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        // 페이지 번호와 페이지 크기를 사용하여 PageRequest 객체 생성
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);

        // Page 객체를 사용하여 숙소 목록 조회
        Page<Accommodation> fetchedAccommodations = accommodationRepository.findAll(pageRequest);

        // 조회된 숙소 목록을 DTO로 변환
        List<FetchedAccommodation> accommodations = fetchedAccommodations.getContent().stream()
                .map(accommodation -> FetchedAccommodation.builder()
                        .id(accommodation.getId())
                        .name(accommodation.getName())
                        .location(accommodation.getLocation())
                        .description(accommodation.getDescription())
                        .price(accommodation.getPrice())
                        .rating(accommodation.getRating())
                        .thumbnailPath(accommodation.getThumbnailPath())
                        .build())
                .toList();

        return FetchAccommodationsResponse.builder()
                .accommodations(accommodations)
                .currentPage(fetchedAccommodations.getNumber())
                .totalPages(fetchedAccommodations.getTotalPages())
                .totalElements(fetchedAccommodations.getTotalElements())
                .build();
    }
}
