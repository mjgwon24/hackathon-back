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
            String description,
            List<String> imagePathList
    ) {
        public SalePost toEntity() {
            return SalePost.builder()
                    .name(this.name)
                    .category(this.category)
                    .description(this.description)
                    .imagePathList(this.imagePathList)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .likeCount(0)
                    .build();
        }
    }

    @Builder
    public record SalePostResponse(
            Long id,
            String name,
            String category,
            String description,
            List<String> imagePathList,
            LocalDateTime createAt,
            LocalDateTime updateAt,
            int likeCount
    ) {}
}
