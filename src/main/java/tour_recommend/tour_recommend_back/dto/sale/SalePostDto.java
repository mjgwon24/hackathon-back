package tour_recommend.tour_recommend_back.dto.sale;

import lombok.Builder;
import tour_recommend.tour_recommend_back.entity.sale.PurchaseHistory;
import tour_recommend.tour_recommend_back.entity.sale.SalePost;

import java.time.LocalDateTime;
import java.util.List;

public record SalePostDto() {
    @Builder
    public record CreateSalePostRequest(
            String name,
            String category,
            String shortDescription,
            String detailedDescription,
            Double price,
            String sellerName,
            String thumbnailPath,
            Double rating,
            List<String> imagePathList
    ) {
        public SalePost toEntity() {
            return SalePost.builder()
                    .name(this.name)
                    .category(this.category)
                    .shortDescription(this.shortDescription)
                    .detailedDescription(this.detailedDescription)
                    .price(this.price)
                    .sellerName(this.sellerName)
                    .rating(this.rating)
                    .imagePathList(this.imagePathList)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
    }

    @Builder
    public record SalePostResponse(
            Long id,
            String name,
            String category,
            String shortDescription,
            String detailedDescription,
            Double price,
            String sellerName,
            String thumbnailPath,
            Double rating,
            List<String> imagePathList,
            LocalDateTime createAt,
            LocalDateTime updateAt
    ) {}

    @Builder
    public record FetchSalePostsResponse(
        List<FetchedSalePost> salePosts,
        int currentPage,
        int totalPages,
        Long totalElements
    ) {
        @Builder
        public record FetchedSalePost(
                Long id,
                String name,
                String category,
                String shortDescription,
                Double price,
                String sellerName,
                String thumbnailPath,
                Double rating,
                LocalDateTime createAt,
                LocalDateTime updateAt
        ) {}
    }

    @Builder
    public record FetchSalePostsByCategoryResponse(
            List<FetchedSalePostByCategory> salePostsByCategory,
            int currentPage,
            int totalPages,
            Long totalElements
    ) {
        @Builder
        public record FetchedSalePostByCategory(
                Long id,
                String name,
                String category,
                String shortDescription,
                Double price,
                String sellerName,
                String thumbnailPath,
                Double rating,
                LocalDateTime createAt,
                LocalDateTime updateAt
        ) {}
    }

    @Builder
    public record CreatePurchaseRequest(
            String phoneNumber,
            String email,
            Double price,
            Integer quantity
    ) {
        public PurchaseHistory toEntity(SalePost salePost) {
            return PurchaseHistory.builder()
                    .category(salePost.getCategory())
                    .phoneNumber(this.phoneNumber)
                    .email(this.email)
                    .name(salePost.getName())
                    .price(this.price)
                    .quantity(this.quantity)
                    .totalPrice(this.price * this.quantity)
                    .sellerName(salePost.getSellerName())
                    .salePost(salePost)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
    }

    @Builder
    public record CreatePurchaseResponse(
            Long id,
            String category,
            String phoneNumber,
            String email,
            String name,
            Double price,
            Integer quantity,
            Double totalPrice,
            String sellerName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record FetchPurchaseHistoryRequest(
        String phoneNumber
    ) {}

    @Builder
    public record FetchPurchaseHistoriesResponse(
        List<FetchedPurchaseHistory> purchaseHistories
    ) {
        @Builder
        public record FetchedPurchaseHistory(
                Long id,
                String category,
                String phoneNumber,
                String email,
                String name,
                Double price,
                Integer quantity,
                Double totalPrice,
                String sellerName,
                String salePostThumbnailPath,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {}
    }
}
