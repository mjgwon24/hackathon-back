package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tour_recommend.tour_recommend_back.dto.ResponseDto;
import tour_recommend.tour_recommend_back.dto.ResponseDto.Status;
import tour_recommend.tour_recommend_back.dto.ReviewPostDto.CreateReviewPostRequest;
import tour_recommend.tour_recommend_back.dto.ReviewPostDto.ReviewPostResponse;
import tour_recommend.tour_recommend_back.service.ReviewPostService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewPostApi {
    private final ReviewPostService reviewPostService;

    // 후기 등록
    @PostMapping("/posts")
    public ResponseEntity<ResponseDto<ReviewPostResponse>> createReviewPost(@RequestBody CreateReviewPostRequest createReviewPostRequest) {
        ReviewPostResponse reviewPostResponse = reviewPostService.createReviewPost(createReviewPostRequest);

        return ResponseEntity.ok(
                new ResponseDto<>(Status.SUCCESS, "후기 등록 성공", reviewPostResponse)
        );
    }

    // 모든 후기 조회
    @GetMapping("/posts")
    public ResponseEntity<ResponseDto<List<ReviewPostResponse>>> getAllReviewPosts() {
        List<ReviewPostResponse> reviewDtos = reviewPostService.getAllReviewPosts();

        return ResponseEntity.ok(
                new ResponseDto<>(Status.SUCCESS, "모든 후기 조회 성공", reviewDtos)
        );
    }
}
