package tour_recommend.tour_recommend_back.dto.sale;

import lombok.Builder;
import tour_recommend.tour_recommend_back.entity.sale.SaleComment;
import tour_recommend.tour_recommend_back.entity.sale.SalePost;

import java.time.LocalDateTime;
import java.util.List;

public record SaleCommentDto() {
    @Builder
    public record CreateSaleCommentRequest (
            String author,
            String contents
    ) {
        public SaleComment toEntity(SalePost salePost) {
            return SaleComment.builder()
                    .author(this.author)
                    .contents(this.contents)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .salePost(salePost)
                    .build();
        }
    }

    @Builder
    public record CreateSaleCommentResponse (
            Long id,
            String author,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record UpdateSaleCommentRequest (
            String author,
            String contents
    ) {
        public SaleComment toEntity(SaleComment comment) {
            return SaleComment.builder()
                    .id(comment.getId())
                    .author(this.author)
                    .contents(this.contents)
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .salePost(comment.getSalePost()) // post: nullable = false
                    .build();
        }
    }

    @Builder
    public record UpdateSaleCommentResponse (
            Long id,
            String author,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record FetchSaleCommentsResponse (
            List<FetchedSaleComment> posts,
            int currentPage,
            int totalPages,
            Long totalElements
    ) {
        @Builder
        public record FetchedSaleComment (
                Long id,
                String author,
                String contents,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {}
    }
}