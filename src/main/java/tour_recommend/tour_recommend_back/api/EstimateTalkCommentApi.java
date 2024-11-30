package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkCommentDto;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkCommentDto.FetchEstimateTalkCommentsResponse;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkCommentDto.CreateEstimateTalkCommentResponse;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkCommentDto.CreateEstimateTalkCommentRequest;
import tour_recommend.tour_recommend_back.service.EstimateTalkCommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estimate-talk")
public class EstimateTalkCommentApi {
    private final EstimateTalkCommentService estimateTalkCommentService;

    // 댓글 생성
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<CreateEstimateTalkCommentResponse>> createEstimateTalkComment(@PathVariable("postId") Long postId,
                                                                                                                           @RequestBody CreateEstimateTalkCommentRequest createEstimateTalkCommentRequest) {
        CreateEstimateTalkCommentResponse createEstimateTalkCommentResponse = estimateTalkCommentService.createEstimateTalkComment(postId, createEstimateTalkCommentRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "게시글 댓글 등록 성공", createEstimateTalkCommentResponse),
                HttpStatus.OK
        );
    }

    // 댓글 업데이트
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDto<CreateEstimateTalkCommentResponse>> updateEstimateTalkComment(@PathVariable("commentId") Long commentId,
                                                                                                                           @RequestBody CreateEstimateTalkCommentRequest createEstimateTalkCommentRequest) {
        CreateEstimateTalkCommentResponse createEstimateTalkCommentResponse = estimateTalkCommentService.createEstimateTalkComment(commentId, createEstimateTalkCommentRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "댓글 수정 성공", createEstimateTalkCommentResponse),
                HttpStatus.OK
        );
    }

    // 댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<FetchEstimateTalkCommentsResponse>> fetchEstimateTalkComments(@PathVariable("postId") Long postId,
                                                                                                                           @RequestParam(defaultValue = "0", name = "pageNumber") int pageNumber,
                                                                                                                           @RequestParam(defaultValue = "10", name = "size") int size) {
        FetchEstimateTalkCommentsResponse fetchEstimateTalkCommentsResponse = estimateTalkCommentService.fetchEstimateTalkComments(postId, pageNumber, size);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "댓글 조회 성공", fetchEstimateTalkCommentsResponse),
                HttpStatus.OK
        );
    }
}
