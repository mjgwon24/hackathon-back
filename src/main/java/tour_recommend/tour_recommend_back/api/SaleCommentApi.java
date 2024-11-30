package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto.Status;
import tour_recommend.tour_recommend_back.dto.sale.SaleCommentDto.*;
import tour_recommend.tour_recommend_back.service.SaleCommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sale")
public class SaleCommentApi {
    private final SaleCommentService saleCommentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<CreateSaleCommentResponse>> createSaleComment(@PathVariable("postId") Long postId,
                                                                            @RequestBody CreateSaleCommentRequest createSaleCommentRequest) {
        CreateSaleCommentResponse createCommentResponse = saleCommentService.createSaleComment(postId, createSaleCommentRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "게시글 댓글 등록 성공", createCommentResponse),
                HttpStatus.OK
        );
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDto<UpdateSaleCommentResponse>> updateSaleComment(@PathVariable("commentId") Long commentId,
                                                                                @RequestBody UpdateSaleCommentRequest updateSaleCommentRequest) {
        UpdateSaleCommentResponse updateSaleCommentResponse = saleCommentService.updateSaleComment(commentId, updateSaleCommentRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "댓글 수정 성공", updateSaleCommentResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<FetchSaleCommentsResponse>> fetchSaleComments(@PathVariable("postId") Long postId,
                                                                                @RequestParam(defaultValue = "0") int pageNumber,
                                                                                @RequestParam(defaultValue = "10") int size) {
        FetchSaleCommentsResponse fetchSaleCommentsResponse = saleCommentService.fetchSaleComments(postId, pageNumber, size);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "댓글 조회 성공", fetchSaleCommentsResponse),
                HttpStatus.OK
        );
    }
}
