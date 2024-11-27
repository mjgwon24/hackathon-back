package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tour_recommend.tour_recommend_back.dto.accommodation.accommodationDto.FetchAccommodationResponse;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto;
import tour_recommend.tour_recommend_back.service.ReservationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationAPI {
    private final ReservationService reservationService;

    @GetMapping("/accommodations/{accommodationId}")
    public ResponseEntity<ResponseDto<FetchAccommodationResponse>> fetchAccommodation(@PathVariable("accommodationId") Long accommodationId) {
        FetchAccommodationResponse fetchAccommodationResponse = reservationService.fetchAccommodation(accommodationId);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "숙소 조회 성공", fetchAccommodationResponse)
        );
    }
}
