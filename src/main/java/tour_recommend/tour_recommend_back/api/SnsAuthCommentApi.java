package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthCommentDto.*;
import tour_recommend.tour_recommend_back.service.SnsAuthCommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sns-auth")
public class SnsAuthCommentApi {
    private final SnsAuthCommentService snsAuthCommentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<CreateSnsAuthCommentResponse>> createSnsAuthComment(@PathVariable("postId") Long salePostId,
                                                                                       @RequestBody CreateSnsAuthCommentRequest createSnsAuthCommentRequest) {
        CreateSnsAuthCommentResponse createSnsAuthCommentResponse = snsAuthCommentService.createSnsAuthComment(salePostId, createSnsAuthCommentRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "게시글 댓글 등록 성공", createSnsAuthCommentResponse),
                HttpStatus.OK
        );
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDto<UpdateSnsAuthCommentResponse>> updateSnsAuthComment(@PathVariable("commentId") Long saleCommentId,
                                                                                       @RequestBody UpdateSnsAuthCommentRequest updateSnsAuthCommentRequest) {
        UpdateSnsAuthCommentResponse updateSaleCommentResponse = snsAuthCommentService.updateSnsAuthComment(saleCommentId, updateSnsAuthCommentRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "댓글 수정 성공", updateSaleCommentResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<FetchSnsAuthCommentsResponse>> fetchSnsAuthComments(@PathVariable("postId") Long postId,
                                                                                       @RequestParam(defaultValue = "0", name = "pageNumber") int pageNumber,
                                                                                       @RequestParam(defaultValue = "10", name = "size") int size) {
        FetchSnsAuthCommentsResponse fetchSnsAuthCommentsResponse = snsAuthCommentService.fetchSnsAuthComments(postId, pageNumber, size);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "댓글 조회 성공", fetchSnsAuthCommentsResponse),
                HttpStatus.OK
        );
    }
}
