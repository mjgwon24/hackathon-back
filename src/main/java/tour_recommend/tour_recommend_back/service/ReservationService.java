package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.*;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccomReservationsRes.*;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationsResponse.FetchedAccommodation;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationResponse.FetchedReservation;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationResponse.FetchedRoom;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationResponse;
import tour_recommend.tour_recommend_back.dto.campsite.CampsiteDto.*;
import tour_recommend.tour_recommend_back.dto.campsite.CampsiteDto.FetchCampsiteReservationRes.FetchedCampsiteReservation;
import tour_recommend.tour_recommend_back.dto.campsite.CampsiteDto.FetchCampsitesResponse.FetchedCampsite;
import tour_recommend.tour_recommend_back.dto.campsite.CampsiteDto.FetchCampsitesResponse;
import tour_recommend.tour_recommend_back.dto.campsite.CampsiteDto.FetchCampsiteResponse;
import tour_recommend.tour_recommend_back.entity.accommodation.Accommodation;
import tour_recommend.tour_recommend_back.entity.accommodation.AccommodationReservation;
import tour_recommend.tour_recommend_back.entity.accommodation.Room;
import tour_recommend.tour_recommend_back.entity.accommodation.RoomAvailability;
import tour_recommend.tour_recommend_back.entity.campsite.Campsite;
import tour_recommend.tour_recommend_back.entity.campsite.CampsiteAvailability;
import tour_recommend.tour_recommend_back.entity.campsite.CampsiteReservation;
import tour_recommend.tour_recommend_back.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {
    private final AccommodationRepository accommodationRepository;
    private final CampsiteRepository campsiteRepository;
    private final RoomAvailabilityRepository roomAvailabilityRepository;
    private final AccommodationReservationRepository accommodationReservationRepository;
    private final CampsiteAvailabilityRepository campsiteAvailabilityRepository;
    private final CampsiteReservationRepository campsiteReservationRepository;

    // 숙소 조회
    public FetchAccommodationResponse fetchAccommodation(Long accommodationId) {
        Accommodation fetchedAccommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new RuntimeException("accommodationId에 해당하는 숙소가 존재하지 않습니다."));

        List<FetchedRoom> rooms = fetchedAccommodation.getRooms().stream()
                .map(room -> FetchedRoom.builder()
                        .id(room.getId())
                        .roomType(room.getRoomType())
                        .build())
                .toList();

        List<FetchedReservation> reservations = fetchedAccommodation.getAccommodationReservations().stream()
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

    // 날짜에 따른 남은 숙소 방 수 조회
    public int fetchAvailableRooms(Long accommodationId, LocalDate checkInDate, LocalDate checkOutDate) {
        try {
            // 숙소 조회
            Accommodation fetchedAccommodation = accommodationRepository.findById(accommodationId)
                    .orElseThrow(() -> new RuntimeException("accommodationId에 해당하는 숙소가 존재하지 않습니다."));

            // Room 인스턴스 조회
            List<Room> rooms = fetchedAccommodation.getRooms();

            // Room들의 roomId 기준으로 RoomAvailability 조회
            List<RoomAvailability> roomAvailabilities = rooms.stream()
                    .map(Room::getAvailabilities)
                    .flatMap(List::stream)
                    .toList();

            // 체크인 날짜와 체크아웃-1 날짜 사이의 RoomAvailability만 필터링
            List<RoomAvailability> filteredRoomAvailabilities = roomAvailabilities.stream()
                    .filter(roomAvailability -> roomAvailability.getDate().isAfter(checkInDate.minusDays(1)))
                    .filter(roomAvailability -> roomAvailability.getDate().isBefore(checkOutDate))
                    .toList();

            // 필터링된 RoomAvailability가 없을 경우, 예약 가능한 방 수가 없다고 판단, 0 반환
            if (filteredRoomAvailabilities.isEmpty()) {
                return 0;
            }

            // RoomAvailability를 RoomId 별로 그룹화하여 각 RoomId에 대해 최소 availableCount를 계산
            Map<Long, Integer> minAvailableRoomsByRoomId = filteredRoomAvailabilities.stream()
                    .collect(Collectors.groupingBy(
                            roomAvailability -> roomAvailability.getRoom().getId(),
                            Collectors.mapping(RoomAvailability::getAvailableCount,
                                    Collectors.minBy(Integer::compareTo)
                            )
                    ))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue().isPresent())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().get()
                    ));

            // 각 roomId 별 최소 availableCount를 합산하여 최종 전체 남은 방 수 반환
            return minAvailableRoomsByRoomId.values().stream()
                    .mapToInt(Integer::intValue)
                    .sum();
        } catch (RuntimeException e) {
            // 예외 발생 시, 0 반환
            System.err.println(e.getMessage());
            return 0;
        }
    }

    // 숙소 예약
    @Transactional
    public void reserveAccommodation(Long accommodationId, String phoneNumber, Double totalPrice, LocalDate checkInDate, LocalDate checkOutDate) {
        // 숙소 조회
        Accommodation fetchedAccommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new RuntimeException("accommodationId에 해당하는 숙소가 존재하지 않습니다."));

        // Room 조회 - Room 리스트를 순회하며 예약 가능한 Room을 찾음
        Room rooms = fetchedAccommodation.getRooms().stream()
                .filter(room -> room.getAvailabilities().stream()
                        .anyMatch(roomAvailability -> roomAvailability.getDate().isEqual(checkInDate.minusDays(1))
                                && roomAvailability.getAvailableCount() > 0))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("예약 가능한 방이 없습니다."));

        // RoomAvailability 조회 및 필터링 (체크인 날짜와 체크아웃-1 날짜 사이의 RoomAvailability만 필터링)
        List<RoomAvailability> filteredRoomAvailabilities = rooms.getAvailabilities().stream()
                .filter(roomAvailability -> roomAvailability.getDate().isAfter(checkInDate.minusDays(1)))
                .filter(roomAvailability -> roomAvailability.getDate().isBefore(checkOutDate))
                .toList();

        // 필터링된 RoomAvailability가 없을 경우, 예약 가능한 방 수가 없다고 판단, 예외 발생
        if (filteredRoomAvailabilities.isEmpty()) {
            log.info("예약 가능한 방이 없습니다.");
            throw new RuntimeException("예약 가능한 방이 없습니다.");
        }

        // AccommodationReservation 생성 - checkInDate와 checkOutDate - 1 사이 날짜를 모두 생성
        for (LocalDate date = checkInDate; date.isBefore(checkOutDate); date = date.plusDays(1)) {
            AccommodationReservation accommodationReservation = AccommodationReservation.builder()
                    .phoneNumber(phoneNumber)
                    .checkInDate(date.atStartOfDay())
                    .checkOutDate(checkOutDate.atStartOfDay())
                    .totalPrice(totalPrice)
                    .room(rooms)
                    .accommodation(fetchedAccommodation)
                    .build();

            // AccommodationReservation 저장
            accommodationReservationRepository.save(accommodationReservation);
        }

        // filteredRoomAvailabilities 리스트를 순회하며 availableCount를 1 감소
        for (RoomAvailability roomAvailability : filteredRoomAvailabilities) {
            log.info("roomAvailability: {}", roomAvailability);
            roomAvailabilityRepository.decreaseAvailableCountByRoomIdAndDate(roomAvailability.getRoom().getId(), roomAvailability.getDate());
        }
    }

    public FetchAccomReservationsRes fetchAccommodationReservations(String phoneNumber) {
        List<AccommodationReservation> fetchedAccomReservations =
                accommodationReservationRepository.findByPhoneNumberOrderByIdDesc(phoneNumber);

        List<FetchedAccomReservation> accomReservations = fetchedAccomReservations.stream()
                .map(accommodationReservation -> FetchedAccomReservation.builder()
                        .id(accommodationReservation.getId())
                        .phoneNumber(accommodationReservation.getPhoneNumber())
                        .email(accommodationReservation.getEmail())
                        .accommodationName(accommodationReservation.getAccommodation().getName())
                        .accommodationDescription(accommodationReservation.getAccommodation().getDescription())
                        .accommodationThumbnailPath(accommodationReservation.getAccommodation().getThumbnailPath())
                        .checkInDate(accommodationReservation.getCheckInDate())
                        .checkOutDate(accommodationReservation.getCheckOutDate())
                        .totalPrice(accommodationReservation.getTotalPrice())
                        .build())
                .toList();

        return FetchAccomReservationsRes.builder()
                .accomReservations(accomReservations)
                .build();
    }

    // 캠핑장 조회
    public FetchCampsiteResponse fetchCampsite(Long campsiteId) {
        Campsite fetchedCampsite = campsiteRepository.findById(campsiteId)
                .orElseThrow(() -> new RuntimeException("campsiteId에 해당하는 캠핑장이 존재하지 않습니다."));

        return FetchCampsiteResponse.builder()
                .id(fetchedCampsite.getId())
                .name(fetchedCampsite.getName())
                .location(fetchedCampsite.getLocation())
                .description(fetchedCampsite.getDescription())
                .price(fetchedCampsite.getPrice())
                .rating(fetchedCampsite.getRating())
                .thumbnailPath(fetchedCampsite.getThumbnailPath())
                .build();
    }

    // 캠핑장 목록 조회
    public FetchCampsitesResponse fetchCampsites(int pageNumber, int size) {
        // Sort 객체 생성하여 정렬 기준 설정
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        // 페이지 번호와 페이지 크기를 사용하여 PageRequest 객체 생성
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);

        // Page 객체를 사용하여 캠핑장 목록 조회
        Page<Campsite> fetchedCampsites = campsiteRepository.findAll(pageRequest);

        // 조회된 캠핑장 목록을 DTO로 변환
        List<FetchedCampsite> campsites = fetchedCampsites.getContent().stream()
                .map(campsite -> FetchedCampsite.builder()
                        .id(campsite.getId())
                        .name(campsite.getName())
                        .location(campsite.getLocation())
                        .description(campsite.getDescription())
                        .price(campsite.getPrice())
                        .rating(campsite.getRating())
                        .thumbnailPath(campsite.getThumbnailPath())
                        .build())
                .toList();

        return FetchCampsitesResponse.builder()
                .campsites(campsites)
                .currentPage(fetchedCampsites.getNumber())
                .totalPages(fetchedCampsites.getTotalPages())
                .totalElements(fetchedCampsites.getTotalElements())
                .build();
    }

    // 날짜에 따른 남은 캠핑장 방 수 조회
    public int fetchAvailableCampsites(Long campsiteId, LocalDate checkInDate, LocalDate checkOutDate) {
        try {
            // 캠핑장 조회
            Campsite fetchedCampsite = campsiteRepository.findById(campsiteId)
                    .orElseThrow(() -> new RuntimeException("campsiteId에 해당하는 캠핑장이 존재하지 않습니다."));

            // CampsiteAvailability 조회
            List<CampsiteAvailability> campsiteAvailabilities = fetchedCampsite.getAvailabilities();

            // 체크인 날짜와 체크아웃-1 날짜 사이의 CampsiteAvailability만 필터링
            List<CampsiteAvailability> filteredCampsiteAvailabilities = campsiteAvailabilities.stream()
                    .filter(campsiteAvailability -> campsiteAvailability.getDate().isAfter(checkInDate.minusDays(1)))
                    .filter(campsiteAvailability -> campsiteAvailability.getDate().isBefore(checkOutDate))
                    .toList();

            // 필터링된 CampsiteAvailability가 없을 경우, 예약 가능한 방 수가 없다고 판단, 0 반환
            if (filteredCampsiteAvailabilities.isEmpty()) {
                return 0;
            }

            // CampsiteAvailability campsiteId 별로 그룹화하여 각 campsiteId에 대해 최소 availableCount를 계산
            Map<Long, Integer> minAvailableCampsitesByCampsiteId = filteredCampsiteAvailabilities.stream()
                    .collect(Collectors.groupingBy(
                            campsiteAvailability -> campsiteAvailability.getCampsite().getId(),
                            Collectors.mapping(CampsiteAvailability::getAvailableCount,
                                    Collectors.minBy(Integer::compareTo)
                            )
                    ))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue().isPresent())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().get()
                    ));

            // 각 campsiteId 별 최소 availableCount를 합산하여 최종 전체 남은 방 수 반환
            return minAvailableCampsitesByCampsiteId.values().stream()
                    .mapToInt(Integer::intValue)
                    .sum();
        } catch (RuntimeException e) {
            // 예외 발생 시, 0 반환
            System.err.println(e.getMessage());
            return 0;
        }
    }

    // 캠핑장 예약
    @Transactional
    public void reserveCampsite(Long campsiteId, String phoneNumber, Double totalPrice, LocalDate checkInDate, LocalDate checkOutDate) {
        // 캠핑장 조회
        Campsite fetchedCampsite = campsiteRepository.findById(campsiteId)
                .orElseThrow(() -> new RuntimeException("campsiteId에 해당하는 캠핑장이 존재하지 않습니다."));

        // CampsiteAvailability 조회 및 필터링 (체크인 날짜와 체크아웃-1 날짜 사이의 CampsiteAvailability만 필터링)
        List<CampsiteAvailability> filteredCampsiteAvailabilities = fetchedCampsite.getAvailabilities().stream()
                .filter(campsiteAvailability -> campsiteAvailability.getDate().isAfter(checkInDate.minusDays(1)))
                .filter(campsiteAvailability -> campsiteAvailability.getDate().isBefore(checkOutDate))
                .toList();

        // 필터링된 CampsiteAvailability가 없을 경우, 예약 가능한 방 수가 없다고 판단, 예외 발생
        if (filteredCampsiteAvailabilities.isEmpty()) {
            throw new RuntimeException("예약 가능한 방이 없습니다.");
        }

        // CampsiteReservation 생성 - checkInDate와 checkOutDate - 1 사이 날짜를 모두 생성
        for (LocalDate date = checkInDate; date.isBefore(checkOutDate); date = date.plusDays(1)) {
            CampsiteReservation campsiteReservation = CampsiteReservation.builder()
                    .phoneNumber(phoneNumber)
                    .checkInDate(date.atStartOfDay())
                    .checkOutDate(checkOutDate.atStartOfDay())
                    .totalPrice(totalPrice)
                    .campsite(fetchedCampsite)
                    .build();

            // CampsiteReservation 저장
            campsiteReservationRepository.save(campsiteReservation);
        }

        // filteredCampsiteAvailabilities 리스트를 순회하며 availableCount를 1 감소
        for (CampsiteAvailability campsiteAvailability : filteredCampsiteAvailabilities) {
            campsiteAvailabilityRepository.decreaseAvailableCountByCampsiteIdAndDate(campsiteAvailability.getCampsite().getId(), campsiteAvailability.getDate());
        }
    }

    public FetchCampsiteReservationRes fetchCampsiteReservations(String phoneNumber) {
        List<CampsiteReservation> fetchedCampsiteReservations =
                campsiteReservationRepository.findByPhoneNumberOrderByIdDesc(phoneNumber);

        List<FetchedCampsiteReservation> campsiteReservations = fetchedCampsiteReservations.stream()
                .map(campsiteReservation -> FetchedCampsiteReservation.builder()
                        .id(campsiteReservation.getId())
                        .phoneNumber(campsiteReservation.getPhoneNumber())
                        .campsiteName(campsiteReservation.getCampsite().getName())
                        .campsiteDescription(campsiteReservation.getCampsite().getDescription())
                        .campsiteThumbnailPath(campsiteReservation.getCampsite().getThumbnailPath())
                        .checkInDate(campsiteReservation.getCheckInDate())
                        .checkOutDate(campsiteReservation.getCheckOutDate())
                        .totalPrice(campsiteReservation.getTotalPrice())
                        .build())
                .toList();

        return FetchCampsiteReservationRes.builder()
                .campsiteReservations(campsiteReservations)
                .build();
    }
}
