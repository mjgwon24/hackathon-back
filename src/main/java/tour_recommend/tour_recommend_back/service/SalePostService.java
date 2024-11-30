package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.sale.SalePostDto.*;
import tour_recommend.tour_recommend_back.dto.sale.SalePostDto.FetchPurchaseHistoriesResponse.*;
import tour_recommend.tour_recommend_back.dto.sale.SalePostDto.FetchSalePostsByCategoryResponse.FetchedSalePostByCategory;
import tour_recommend.tour_recommend_back.dto.sale.SalePostDto.FetchSalePostsResponse.FetchedSalePost;
import tour_recommend.tour_recommend_back.dto.sale.SalePostDto.FetchSalePostsByCategoryResponse;
import tour_recommend.tour_recommend_back.dto.sale.SalePostDto.FetchSalePostsResponse;
import tour_recommend.tour_recommend_back.dto.sale.SalePostDto.CreateSalePostRequest;
import tour_recommend.tour_recommend_back.dto.sale.SalePostDto.SalePostResponse;
import tour_recommend.tour_recommend_back.entity.sale.PurchaseHistory;
import tour_recommend.tour_recommend_back.entity.sale.SalePost;
import tour_recommend.tour_recommend_back.repository.PurchaseHistoryRepository;
import tour_recommend.tour_recommend_back.repository.SalePostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SalePostService {
    private final SalePostRepository salePostRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    // 판매 게시글 등록
    public SalePostResponse createSalePost(CreateSalePostRequest createSalePostRequest) {
        SalePost salePost = CreateSalePostRequest.builder()
                .name(createSalePostRequest.name())
                .category(createSalePostRequest.category())
                .shortDescription(createSalePostRequest.shortDescription())
                .detailedDescription(createSalePostRequest.detailedDescription())
                .price(createSalePostRequest.price())
                .sellerName(createSalePostRequest.sellerName())
                .thumbnailPath(createSalePostRequest.thumbnailPath())
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
                .thumbnailPath(salePostPs.getThumbnailPath())
                .rating(salePostPs.getRating())
                .imagePathList(salePostPs.getImagePathList())
                .createAt(salePostPs.getCreatedAt())
                .updateAt(salePostPs.getUpdatedAt())
                .build();
    }

    // 판매 게시글 조회
    public SalePostResponse fetchSalePost(Long salePostId) {
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
                .thumbnailPath(salePostPs.getThumbnailPath())
                .rating(salePostPs.getRating())
                .imagePathList(salePostPs.getImagePathList())
                .createAt(salePostPs.getCreatedAt())
                .updateAt(salePostPs.getUpdatedAt())
                .build();
    }

    public FetchSalePostsResponse fetchSalePosts(int pageNumber, int size) {
        // Sort 객체를 생성하여 정렬 기준을 설정합니다.
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        // 페이지 번호와 페이지 크기를 사용하여 PageRequest 객체를 생성합니다.
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);

        Page<SalePost> fetchedSalePosts = salePostRepository.findAll(pageRequest);

        List<FetchedSalePost> salePosts = fetchedSalePosts.get()
                .map(salePost -> FetchedSalePost.builder()
                        .id(salePost.getId())
                        .name(salePost.getName())
                        .category(salePost.getCategory())
                        .shortDescription(salePost.getShortDescription())
                        .price(salePost.getPrice())
                        .sellerName(salePost.getSellerName())
                        .rating(salePost.getRating())
                        .thumbnailPath(salePost.getThumbnailPath())
                        .createAt(salePost.getCreatedAt())
                        .updateAt(salePost.getUpdatedAt())
                        .build())
                .toList();

        return FetchSalePostsResponse.builder()
                .salePosts(salePosts)
                .currentPage(fetchedSalePosts.getNumber())
                .totalPages(fetchedSalePosts.getTotalPages())
                .totalElements(fetchedSalePosts.getTotalElements())
                .build();
    }

    public FetchSalePostsByCategoryResponse getSalePostsByCategory(String category, int pageNumber, int size) {
        // Sort 객체를 생성하여 정렬 기준을 설정합니다.
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        // 페이지 번호와 페이지 크기를 사용하여 PageRequest 객체를 생성합니다.
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);

        Page<SalePost> fetchedSalePostsByCategory = salePostRepository.findByCategory(category, pageRequest);

        List<FetchedSalePostByCategory> salePostsByCategory = fetchedSalePostsByCategory.get()
                .map(salePostByCategory -> FetchedSalePostByCategory.builder()
                        .id(salePostByCategory.getId())
                        .name(salePostByCategory.getName())
                        .category(salePostByCategory.getCategory())
                        .shortDescription(salePostByCategory.getShortDescription())
                        .price(salePostByCategory.getPrice())
                        .sellerName(salePostByCategory.getSellerName())
                        .thumbnailPath(salePostByCategory.getThumbnailPath())
                        .rating(salePostByCategory.getRating())
                        .createAt(salePostByCategory.getCreatedAt())
                        .updateAt(salePostByCategory.getUpdatedAt())
                        .build())
                .toList();

        return FetchSalePostsByCategoryResponse.builder()
                .salePostsByCategory(salePostsByCategory)
                .currentPage(fetchedSalePostsByCategory.getNumber())
                .totalPages(fetchedSalePostsByCategory.getTotalPages())
                .totalElements(fetchedSalePostsByCategory.getTotalElements())
                .build();
    }

    public CreatePurchaseResponse createPurchase(Long postId, CreatePurchaseRequest createPurchaseRequest) {
        SalePost fetchedSalePost = salePostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("postId에 해당하는 후기 게시글이 존재하지 않습니다."));

        PurchaseHistory purchaseHistory = CreatePurchaseRequest.builder()
                .phoneNumber(createPurchaseRequest.phoneNumber())
                .email(createPurchaseRequest.email())
                .price(createPurchaseRequest.price())
                .quantity(createPurchaseRequest.quantity())
                .build()
                .toEntity(fetchedSalePost);

        PurchaseHistory purchaseHistoryPs = purchaseHistoryRepository.save(purchaseHistory);

        return CreatePurchaseResponse.builder()
                .id(purchaseHistoryPs.getId())
                .category(purchaseHistoryPs.getCategory())
                .phoneNumber(purchaseHistoryPs.getPhoneNumber())
                .email(purchaseHistoryPs.getEmail())
                .name(purchaseHistoryPs.getName())
                .price(purchaseHistoryPs.getPrice())
                .quantity(purchaseHistoryPs.getQuantity())
                .totalPrice(purchaseHistoryPs.getTotalPrice())
                .sellerName(purchaseHistoryPs.getSellerName())
                .createdAt(purchaseHistoryPs.getCreatedAt())
                .updatedAt(purchaseHistoryPs.getUpdatedAt())
                .build();
    }

    public FetchPurchaseHistoriesResponse fetchPurchaseHistories(String phoneNumber) {
        List<PurchaseHistory> fetchedPurchaseHistories = purchaseHistoryRepository.findByPhoneNumberOrderByCreatedAtDesc(phoneNumber);

        List<FetchedPurchaseHistory> purchaseHistories = fetchedPurchaseHistories.stream()
                .map(purchaseHistory -> FetchedPurchaseHistory.builder()
                        .id(purchaseHistory.getId())
                        .category(purchaseHistory.getCategory())
                        .phoneNumber(purchaseHistory.getPhoneNumber())
                        .email(purchaseHistory.getEmail())
                        .name(purchaseHistory.getName())
                        .price(purchaseHistory.getPrice())
                        .quantity(purchaseHistory.getQuantity())
                        .totalPrice(purchaseHistory.getTotalPrice())
                        .sellerName(purchaseHistory.getSellerName())
                        .category(purchaseHistory.getCategory())
                        .salePostThumbnailPath(purchaseHistory.getSalePost().getThumbnailPath())
                        .createdAt(purchaseHistory.getCreatedAt())
                        .updatedAt(purchaseHistory.getUpdatedAt())
                        .build())
                .toList();

        return FetchPurchaseHistoriesResponse.builder()
                .purchaseHistories(purchaseHistories)
                .build();
    }

}
