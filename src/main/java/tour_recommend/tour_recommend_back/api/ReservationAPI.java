package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationsResponse;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationResponse;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto;
import tour_recommend.tour_recommend_back.service.ReservationService;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationAPI {
    private final ReservationService reservationService;

    // 숙소 조회
    @GetMapping("/accommodations/{accommodationId}")
    public ResponseEntity<ResponseDto<FetchAccommodationResponse>> fetchAccommodation(@PathVariable("accommodationId") Long accommodationId) {
        FetchAccommodationResponse fetchAccommodationResponse = reservationService.fetchAccommodation(accommodationId);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "숙소 조회 성공", fetchAccommodationResponse)
        );
    }

    // 숙소 목록 조회
    @GetMapping("/accommodations")
    public ResponseEntity<ResponseDto<FetchAccommodationsResponse>> fetchAccommodations(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        FetchAccommodationsResponse fetchAccommodationsResponse = reservationService.fetchAccommodations(pageNumber, size);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "숙소 목록 조회 성공", fetchAccommodationsResponse)
        );
    }

    // 날짜에 따른 남은 방 수 조회
    @GetMapping("/accommodations/{accommodationId}/available-rooms")
    public ResponseEntity<ResponseDto<Integer>> fetchAvailableRooms(@PathVariable("accommodationId") Long accommodationId,
                                                                    @RequestParam("checkInDate") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                                    @RequestParam("checkOutDate") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        int availableRooms = reservationService.fetchAvailableRooms(accommodationId, checkInDate, checkOutDate);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "남은 방 수 조회 성공", availableRooms)
        );
    }
}
