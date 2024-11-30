package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto.Status;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthPostDto.FetchSnsAuthPostsResponse;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthPostDto.FetchSnsAuthPostResponse;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthPostDto.CreateSnsAuthPostRequest;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthPostDto.CreateSnsAuthPostResponse;
import tour_recommend.tour_recommend_back.service.SnsAuthPostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sns-auth")
public class SnsAuthPostApi {
    private final SnsAuthPostService snsAuthPostService;

    @PostMapping("/posts")
    public ResponseEntity<ResponseDto<CreateSnsAuthPostResponse>> createSnsAuthPost(@RequestBody CreateSnsAuthPostRequest createSnsAuthPostRequest) {

        CreateSnsAuthPostResponse createSnsAuthPostResponse = snsAuthPostService.createSnsAuthPost(createSnsAuthPostRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "SNS 인증 게시글 등록 성공", createSnsAuthPostResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResponseDto<FetchSnsAuthPostResponse>> fetchSnsAuthPost(@PathVariable("postId") Long postId) {

        FetchSnsAuthPostResponse fetchSnsAuthPostResponse = snsAuthPostService.fetchSnsAuthPost(postId);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "SNS 인증 게시글 조회 성공", fetchSnsAuthPostResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseDto<FetchSnsAuthPostsResponse>> fetchSnsAuthPosts(@RequestParam(defaultValue = "0", name = "pageNumber") int pageNumber,
                                                                                    @RequestParam(defaultValue = "10", name = "size") int size) {
        FetchSnsAuthPostsResponse fetchSnsAuthPostsResponse = snsAuthPostService.fetchSnsAuthPosts(pageNumber, size);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "SNS 인증 게시글 목록 조회 성공", fetchSnsAuthPostsResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/posts/category/{postType}")
    public ResponseEntity<ResponseDto<FetchSnsAuthPostsResponse>> fetchSnsAuthPostsByPostType(@PathVariable("postType") String postType,
                                                                                              @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                                              @RequestParam(name = "size", defaultValue = "10") int size) {
        FetchSnsAuthPostsResponse fetchSnsAuthPostsResponse = snsAuthPostService.fetchSnsAuthPostsByPostType(postType, pageNumber, size);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "postType별 SNS 인증 게시글 목록 조회 성공", fetchSnsAuthPostsResponse),
                HttpStatus.OK
        );
    }

}
