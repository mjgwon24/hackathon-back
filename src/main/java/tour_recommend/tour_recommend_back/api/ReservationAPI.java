package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.*;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationsResponse;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationResponse;
import tour_recommend.tour_recommend_back.dto.campsite.CampsiteDto.*;
import tour_recommend.tour_recommend_back.dto.campsite.CampsiteDto.FetchCampsitesResponse;
import tour_recommend.tour_recommend_back.dto.campsite.CampsiteDto.FetchCampsiteResponse;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto;
import tour_recommend.tour_recommend_back.service.ReservationService;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
public class ReservationAPI {
    private final ReservationService reservationService;

    // 숙소 조회
    @GetMapping("/reservation/accommodations/{accommodationId}")
    public ResponseEntity<ResponseDto<FetchAccommodationResponse>> fetchAccommodation(@PathVariable("accommodationId") Long accommodationId) {
        FetchAccommodationResponse fetchAccommodationResponse = reservationService.fetchAccommodation(accommodationId);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "숙소 조회 성공", fetchAccommodationResponse)
        );
    }

    // 숙소 목록 조회
    @GetMapping("/reservation/accommodations")
    public ResponseEntity<ResponseDto<FetchAccommodationsResponse>> fetchAccommodations(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        FetchAccommodationsResponse fetchAccommodationsResponse = reservationService.fetchAccommodations(pageNumber, size);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "숙소 목록 조회 성공", fetchAccommodationsResponse)
        );
    }

    // 날짜에 따른 남은 숙소 방 수 조회
    @GetMapping("/reservation/accommodations/{accommodationId}/available-rooms")
    public ResponseEntity<ResponseDto<Integer>> fetchAvailableRooms(@PathVariable("accommodationId") Long accommodationId,
                                                                    @RequestParam("checkInDate") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                                    @RequestParam("checkOutDate") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        int availableRooms = reservationService.fetchAvailableRooms(accommodationId, checkInDate, checkOutDate);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "남은 방 수 조회 성공", availableRooms)
        );
    }

    // 숙소 예약
    @Transactional
    @PostMapping("/reservation/accommodations/{accommodationId}")
    public ResponseEntity<ResponseDto<Long>> reserveAccommodation(@PathVariable("accommodationId") Long accommodationId,
                                                                  @RequestParam("checkInDate") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                                  @RequestParam("checkOutDate") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                                                  @RequestParam("phoneNumber") String phoneNumber,
                                                                  @RequestParam("totalPrice") Double totalPrice) {
        try {
            reservationService.reserveAccommodation(accommodationId, phoneNumber, totalPrice, checkInDate, checkOutDate);

            return ResponseEntity.ok(
                    new ResponseDto<>(ResponseDto.Status.SUCCESS, "숙소 예약 성공", null)
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ResponseDto<>(ResponseDto.Status.FAILURE, "숙소 예약 실패", null)
            );
        }
    }

    // 숙소 예약 조회
    @GetMapping("/accommodation-reservations")
    public ResponseEntity<ResponseDto<FetchAccomReservationsRes>> fetchAccommodationReservations(@RequestParam(name = "phoneNumber") String phoneNumber) {
        FetchAccomReservationsRes fetchAccommodationsResponse = reservationService.fetchAccommodationReservations(phoneNumber);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "숙소 예약 조회 성공", fetchAccommodationsResponse)
        );
    }

    // 캠핑장 조회
    @GetMapping("/reservation/campsites/{campsiteId}")
    public ResponseEntity<ResponseDto<FetchCampsiteResponse>> fetchCampsite(@PathVariable("campsiteId") Long campsiteId) {
        FetchCampsiteResponse fetchCampsiteResponse = reservationService.fetchCampsite(campsiteId);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "캠핑장 조회 성공", fetchCampsiteResponse)
        );
    }

    // 캠핑장 목록 조회
    @GetMapping("/reservation/campsites")
    public ResponseEntity<ResponseDto<FetchCampsitesResponse>> fetchCampsites(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                              @RequestParam(name = "size", defaultValue = "10") int size) {
        FetchCampsitesResponse fetchCampsiteResponse = reservationService.fetchCampsites(pageNumber, size);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "캠핑장 목록 조회 성공", fetchCampsiteResponse)
        );
    }

    // 날짜에 따른 남은 캠핑장 방 수 조회
    @GetMapping("/reservation/campsites/{campsiteId}/available-rooms")
    public ResponseEntity<ResponseDto<Integer>> fetchCampsiteAvailableRooms(@PathVariable("campsiteId") Long campsiteId,
                                                                            @RequestParam("checkInDate") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                                            @RequestParam("checkOutDate") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        int availableRooms = reservationService.fetchAvailableCampsites(campsiteId, checkInDate, checkOutDate);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "남은 방 수 조회 성공", availableRooms)
        );
    }

    // 캠핑장 예약
    @PostMapping("/reservation/campsites/{campsiteId}")
    public ResponseEntity<ResponseDto<Long>> reserveCampsite(@PathVariable("campsiteId") Long campsiteId,
                                                             @RequestParam("checkInDate") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                             @RequestParam("checkOutDate") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                                             @RequestParam("phoneNumber") String phoneNumber,
                                                             @RequestParam("totalPrice") Double totalPrice) {
        try {
            reservationService.reserveCampsite(campsiteId, phoneNumber, totalPrice, checkInDate, checkOutDate);

            return ResponseEntity.ok(
                    new ResponseDto<>(ResponseDto.Status.SUCCESS, "캠핑장 예약 성공", null)
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ResponseDto<>(ResponseDto.Status.FAILURE, "캠핑장 예약 실패", null)
            );
        }
    }

    @GetMapping("/campsite-reservations")
    public ResponseEntity<ResponseDto<FetchCampsiteReservationRes>> fetchCampsiteReservations(@RequestParam(name = "phoneNumber") String phoneNumber) {
        FetchCampsiteReservationRes fetchCampsiteReservationRes = reservationService.fetchCampsiteReservations(phoneNumber);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "캠핑장 예약 조회 성공", fetchCampsiteReservationRes)
        );
    }
}
