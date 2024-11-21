package tour_recommend.tour_recommend_back.dto;

import lombok.Builder;
import tour_recommend.tour_recommend_back.entity.ReviewPost;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewPostDto() {
    @Builder
    public record CreateReviewPostRequest(
            String title,
            String category,
            String contents,
            List<String> imagePathList
    ) {
        public ReviewPost toEntity() {
            return ReviewPost.builder()
                    .title(this.title)
                    .category(this.category)
                    .contents(this.contents)
                    .imagePathList(this.imagePathList)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .likeCount(0)
                    .build();
        }
    }

    @Builder
    public record ReviewPostResponse(
            Long id,
            String title,
            String category,
            String contents,
            List<String> imagePathList,
            LocalDateTime createAt,
            LocalDateTime updateAt,
            int likeCount
    ) {}
}
