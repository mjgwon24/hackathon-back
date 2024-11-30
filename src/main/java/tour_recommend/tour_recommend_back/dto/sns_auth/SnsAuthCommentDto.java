package tour_recommend.tour_recommend_back.dto.sns_auth;

import lombok.Builder;
import tour_recommend.tour_recommend_back.entity.sns_auth.SnsAuthComment;
import tour_recommend.tour_recommend_back.entity.sns_auth.SnsAuthPost;

import java.time.LocalDateTime;
import java.util.List;

public record SnsAuthCommentDto() {
    @Builder
    public record CreateSnsAuthCommentRequest (
            String author,
            String contents
    ) {
        public SnsAuthComment toEntity(SnsAuthPost snsAuthPost) {
            return SnsAuthComment.builder()
                    .author(this.author)
                    .contents(this.contents)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .snsAuthPost(snsAuthPost)
                    .build();
        }
    }

    @Builder
    public record CreateSnsAuthCommentResponse (
            Long id,
            String author,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record UpdateSnsAuthCommentRequest (
            String author,
            String contents
    ) {
        public SnsAuthComment toEntity(SnsAuthComment snsAuthComment) {
            return SnsAuthComment.builder()
                    .id(snsAuthComment.getId())
                    .author(this.author)
                    .contents(this.contents)
                    .createdAt(snsAuthComment.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .snsAuthPost(snsAuthComment.getSnsAuthPost()) // post: nullable = false
                    .build();
        }
    }

    @Builder
    public record UpdateSnsAuthCommentResponse (
            Long id,
            String author,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record FetchSnsAuthCommentsResponse (
            List<FetchedSnsAuthComment> posts,
            int currentPage,
            int totalPages,
            Long totalElements
    ) {
        @Builder
        public record FetchedSnsAuthComment (
                Long id,
                String author,
                String contents,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {}
    }
}