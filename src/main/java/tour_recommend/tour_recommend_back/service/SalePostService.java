package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.SalePostDto.CreateSalePostRequest;
import tour_recommend.tour_recommend_back.dto.SalePostDto.SalePostResponse;
import tour_recommend.tour_recommend_back.entity.SalePost;
import tour_recommend.tour_recommend_back.repository.SalePostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SalePostService {
    private final SalePostRepository salePostRepository;

    // 판매 게시글 등록
    public SalePostResponse createSalePost(CreateSalePostRequest createSalePostRequest) {
        SalePost salePost = CreateSalePostRequest.builder()
                .name(createSalePostRequest.name())
                .category(createSalePostRequest.category())
                .shortDescription(createSalePostRequest.shortDescription())
                .detailedDescription(createSalePostRequest.detailedDescription())
                .price(createSalePostRequest.price())
                .sellerName(createSalePostRequest.sellerName())
                .rating(createSalePostRequest.rating())
                .imagePathList(createSalePostRequest.imagePathList())
                .build()
                .toEntity();

        SalePost salePostPs = salePostRepository.save(salePost);

        return SalePostResponse.builder()
                .id(salePostPs.getId())
                .name(salePostPs.getName())
                .category(salePostPs.getCategory())
                .shortDescription(salePostPs.getShortDescription())
                .detailedDescription(salePostPs.getDetailedDescription())
                .price(salePostPs.getPrice())
                .sellerName(salePostPs.getSellerName())
                .rating(salePostPs.getRating())
                .imagePathList(salePostPs.getImagePathList())
                .createAt(salePostPs.getCreatedAt())
                .updateAt(salePostPs.getUpdatedAt())
                .build();
    }

    // 판매 게시글 조회
    public SalePostResponse getSalePost(Long salePostId) {
        SalePost salePostPs = salePostRepository.findById(salePostId)
                .orElseThrow(() -> new RuntimeException("postId에 해당하는 후기 게시글이 존재하지 않습니다."));

        return SalePostResponse.builder()
                .id(salePostPs.getId())
                .name(salePostPs.getName())
                .category(salePostPs.getCategory())
                .shortDescription(salePostPs.getShortDescription())
                .detailedDescription(salePostPs.getDetailedDescription())
                .price(salePostPs.getPrice())
                .sellerName(salePostPs.getSellerName())
                .rating(salePostPs.getRating())
                .imagePathList(salePostPs.getImagePathList())
                .createAt(salePostPs.getCreatedAt())
                .updateAt(salePostPs.getUpdatedAt())
                .build();
    }

    // 판매 게시글 조회 - createdAt 기준 최신순 반환
    public List<SalePostResponse> getAllSalePosts() {
        return salePostRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(salePost -> SalePostResponse.builder()
                        .id(salePost.getId())
                        .name(salePost.getName())
                        .category(salePost.getCategory())
                        .shortDescription(salePost.getShortDescription())
                        .detailedDescription(salePost.getDetailedDescription())
                        .price(salePost.getPrice())
                        .sellerName(salePost.getSellerName())
                        .rating(salePost.getRating())
                        .imagePathList(salePost.getImagePathList())
                        .createAt(salePost.getCreatedAt())
                        .updateAt(salePost.getUpdatedAt())
                        .build())
                .toList();
    }

    // 카테고리 별 조회 (최신순)
    public List<SalePostResponse> getSalePostsByCategory(String category) {
        return salePostRepository.findAllByCategoryOrderByCreatedAtDesc(category).stream()
                .map(salePost -> SalePostResponse.builder()
                        .id(salePost.getId())
                        .name(salePost.getName())
                        .category(salePost.getCategory())
                        .shortDescription(salePost.getShortDescription())
                        .detailedDescription(salePost.getDetailedDescription())
                        .price(salePost.getPrice())
                        .sellerName(salePost.getSellerName())
                        .rating(salePost.getRating())
                        .imagePathList(salePost.getImagePathList())
                        .createAt(salePost.getCreatedAt())
                        .updateAt(salePost.getUpdatedAt())
                        .build())
                .toList();
    }

}
