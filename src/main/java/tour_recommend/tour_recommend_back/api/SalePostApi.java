package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tour_recommend.tour_recommend_back.dto.ResponseDto;
import tour_recommend.tour_recommend_back.dto.ResponseDto.Status;
import tour_recommend.tour_recommend_back.dto.SalePostDto.CreateSalePostRequest;
import tour_recommend.tour_recommend_back.dto.SalePostDto.SalePostResponse;
import tour_recommend.tour_recommend_back.service.SalePostService;

import java.util.List;

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
    public ResponseEntity<ResponseDto<SalePostResponse>> getSalePost(@PathVariable("salePostId") Long salePostId) {
        SalePostResponse salePostResponse = salePostService.getSalePost(salePostId);

        return ResponseEntity.ok(
                new ResponseDto<>(Status.SUCCESS, "판매 게시글 조회 성공", salePostResponse)
        );
    }

    // 모든 판매 게시글 조회 - 최신순 반환
    @GetMapping("/posts")
    public ResponseEntity<ResponseDto<List<SalePostResponse>>> getAllSalePosts() {
        List<SalePostResponse> saleDtos = salePostService.getAllSalePosts();

        return ResponseEntity.ok(
                new ResponseDto<>(Status.SUCCESS, "판매 게시글 조회 성공", saleDtos)
        );
    }

    // 카테고리별 판매 게시글 조회 - 최신순 반환 & 페이지네이션
    @GetMapping("/posts/category/{category}")
    public ResponseEntity<ResponseDto<List<SalePostResponse>>> getSalePostsByCategory(@PathVariable("category") String category,
                                                                                        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        // 카테고리별 조회 (최신순)
        List<SalePostResponse> saleDtos = salePostService.getSalePostsByCategory(category);

        // 페이지네이션
        int start = pageNumber * size;
        int end = Math.min(start + size, saleDtos.size());
        saleDtos = saleDtos.subList(start, end);

        return ResponseEntity.ok(
                new ResponseDto<>(Status.SUCCESS, "카테고리별 판메 게시글 조회 성공", saleDtos)
        );
    }
}
