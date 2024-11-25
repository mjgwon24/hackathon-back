package tour_recommend.tour_recommend_back.dto;

import lombok.Builder;
import tour_recommend.tour_recommend_back.entity.SalePost;

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
            int rating,
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
            int rating,
            List<String> imagePathList,
            LocalDateTime createAt,
            LocalDateTime updateAt
    ) {}
}
