package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto;
import tour_recommend.tour_recommend_back.dto.common.ResponseDto.Status;
import tour_recommend.tour_recommend_back.dto.post.SalePostDto.FetchSalePostsByCategoryResponse;
import tour_recommend.tour_recommend_back.dto.post.SalePostDto.FetchSalePostsResponse;
import tour_recommend.tour_recommend_back.dto.post.SalePostDto.CreateSalePostRequest;
import tour_recommend.tour_recommend_back.dto.post.SalePostDto.SalePostResponse;
import tour_recommend.tour_recommend_back.service.SalePostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sale")
public class SalePostApi {
    private final SalePostService salePostService;

    // 판매 게시글 등록
    @PostMapping("/posts")
    public ResponseEntity<ResponseDto<SalePostResponse>> createSalePost(@RequestBody CreateSalePostRequest createSalePostRequest) {
        SalePostResponse salePostResponse = salePostService.createSalePost(createSalePostRequest);

        return ResponseEntity.ok(
                new ResponseDto<>(Status.SUCCESS, "판매 게시글 등록 성공", salePostResponse)
        );
    }

    // 판매 게시글 단건 조회
    @GetMapping("/posts/{salePostId}")
    public ResponseEntity<ResponseDto<SalePostResponse>> fetchSalePost(@PathVariable("salePostId") Long salePostId) {
        SalePostResponse salePostResponse = salePostService.fetchSalePost(salePostId);

        return ResponseEntity.ok(
                new ResponseDto<>(Status.SUCCESS, "판매 게시글 조회 성공", salePostResponse)
        );
    }

    // 판매 게시글 조회
    @GetMapping("/posts")
    public ResponseEntity<ResponseDto<FetchSalePostsResponse>> fetchSalePosts(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                              @RequestParam(name = "size", defaultValue = "10") int size) {
        FetchSalePostsResponse fetchedSalePosts = salePostService.fetchSalePosts(pageNumber, size);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "판매 게시글 목록 조회 성공", fetchedSalePosts),
                HttpStatus.OK
        );
    }

    // 카테고리별 판매 게시글 조회
    @GetMapping("/posts/category/{category}")
    public ResponseEntity<ResponseDto<FetchSalePostsByCategoryResponse>> fetchSalePostsByCategory(@PathVariable("category") String category,
                                                                                                              @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                                                              @RequestParam(name = "size", defaultValue = "10") int size) {
        FetchSalePostsByCategoryResponse fetchSalePostsByCategoryResponse = salePostService.getSalePostsByCategory(category, pageNumber, size);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "카테고리별 판매 게시글 목록 조회 성공", fetchSalePostsByCategoryResponse),
                HttpStatus.OK
        );
    }
}
