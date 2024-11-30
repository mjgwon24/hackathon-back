package tour_recommend.tour_recommend_back.dto.estimate_talk;

import lombok.Builder;
import tour_recommend.tour_recommend_back.entity.estimate_talk.EstimateTalkComment;
import tour_recommend.tour_recommend_back.entity.estimate_talk.EstimateTalkPost;

import java.time.LocalDateTime;
import java.util.List;

public record EstimateTalkCommentDto() {
    @Builder
    public record CreateEstimateTalkCommentRequest (
            Long estimateTalkId,
            String author,
            String contents
    ) {
        public EstimateTalkComment toEntity(EstimateTalkPost estimateTalkPost) {
            return EstimateTalkComment.builder()
                    .author(this.author)
                    .contents(this.contents)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .estimateTalkPost(estimateTalkPost)
                    .build();
        }
    }

    @Builder
    public record CreateEstimateTalkCommentResponse (
            Long id,
            String author,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record UpdateEstimateTalkCommentRequest (
            String author,
            String contents
    ) {
        public EstimateTalkComment toEntity(EstimateTalkComment estimateTalkComment) {
            return EstimateTalkComment.builder()
                    .id(estimateTalkComment.getId())
                    .author(this.author)
                    .contents(this.contents)
                    .createdAt(estimateTalkComment.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .estimateTalkPost(estimateTalkComment.getEstimateTalkPost())
                    .build();
        }
    }

    @Builder
    public record UpdateEstimateTalkCommentResponse (
            Long id,
            String author,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record FetchEstimateTalkCommentsResponse (
            List<FetchedEstimateTalkComment> posts,
            int currentPage,
            int totalPage,
            Long totalElements
    ) {
        @Builder
        public record FetchedEstimateTalkComment (
                Long id,
                String author,
                String contents,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {}
    }
}
