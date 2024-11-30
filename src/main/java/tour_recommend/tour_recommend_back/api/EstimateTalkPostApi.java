package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkPostDto.FetchEstimateTalkPostResponse;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkPostDto.FetchEstimateTalkPostsResponse;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkPostDto.CreateEstimateTalkPostRequest;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkPostDto.CreateEstimateTalkPostResponse;
import tour_recommend.tour_recommend_back.service.EstimateTalkPostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estimate-talk")
public class EstimateTalkPostApi {
    private final EstimateTalkPostService estimateTalkPostService;

    // 견적톡 등록
    @PostMapping("/posts")
    public ResponseEntity<ResponseDto<CreateEstimateTalkPostResponse>> createEstimateTalkPost(@RequestBody CreateEstimateTalkPostRequest createEstimateTalkPostRequest) {
        CreateEstimateTalkPostResponse createEstimateTalkPostResponse = estimateTalkPostService.createEstimateTalkPost(createEstimateTalkPostRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "견적톡 게시글 등록 성공", createEstimateTalkPostResponse),
                HttpStatus.OK
        );
    }

    // 견적톡 상세 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResponseDto<FetchEstimateTalkPostResponse>> fetchEstimateTalkPost(@PathVariable("postId") Long postId) {
        FetchEstimateTalkPostResponse fetchEstimateTalkPostResponse = estimateTalkPostService.fetchEstimateTalkPost(postId);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "견적톡 게시글 조회 성공", fetchEstimateTalkPostResponse),
                HttpStatus.OK
        );
    }

    // 견적톡 목록 조회
    @GetMapping("/posts")
    public ResponseEntity<ResponseDto<FetchEstimateTalkPostsResponse>> fetchEstimateTalkPosts(@RequestParam(defaultValue = "0", name = "pageNumber") int pageNumber,
                                                                                               @RequestParam(defaultValue = "10", name = "size") int size) {
        FetchEstimateTalkPostsResponse fetchEstimateTalkPostsResponse = estimateTalkPostService.fetchEstimateTalkPosts(pageNumber, size);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "견적톡 게시글 목록 조회 성공", fetchEstimateTalkPostsResponse),
                HttpStatus.OK
        );
    }
}
