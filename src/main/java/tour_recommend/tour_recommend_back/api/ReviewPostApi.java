package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour_recommend.tour_recommend_back.dto.ResponseDto;
import tour_recommend.tour_recommend_back.dto.ResponseDto.Status;
import tour_recommend.tour_recommend_back.dto.ReviewPostDto.CreateReviewPostRequest;
import tour_recommend.tour_recommend_back.dto.ReviewPostDto.CreateReviewPostResponse;
import tour_recommend.tour_recommend_back.service.ReviewPostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewPostApi {
    private final ReviewPostService reviewPostService;

    @PostMapping("/posts")
    public ResponseEntity<ResponseDto<CreateReviewPostResponse>> createReviewPost(@RequestBody CreateReviewPostRequest createReviewPostRequest) {
        CreateReviewPostResponse createReviewPostResponse = reviewPostService.createReviewPost(createReviewPostRequest);

        return ResponseEntity.ok(
                new ResponseDto<>(Status.SUCCESS, "후기 등록 성공", createReviewPostResponse)
        );
    }
}
