package tour_recommend.tour_recommend_back.dto.estimate_talk;

import lombok.Builder;
import tour_recommend.tour_recommend_back.entity.estimate_talk.EstimateTalkPost;

import java.time.LocalDateTime;
import java.util.List;

public record EstimateTalkPostDto() {
    @Builder
    public record CreateEstimateTalkPostRequest(
            String phoneNumber,
            String email,
            String title,
            String contents,
            List<String> imagePathList
    ) {
        public EstimateTalkPost toEntity() {
            return EstimateTalkPost.builder()
                    .phoneNumber(this.phoneNumber)
                    .email(this.email)
                    .title(this.title)
                    .contents(this.contents)
                    .imagePathList(this.imagePathList)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
    }

    @Builder
    public record CreateEstimateTalkPostResponse(
            Long id,
            String phoneNumber,
            String email,
            String title,
            String contents,
            List<String> imagePathList,
            LocalDateTime createAt,
            LocalDateTime updateAt
    ) {}

    @Builder
    public record FetchEstimateTalkPostResponse(
            Long id,
            String phoneNumber,
            String email,
            String title,
            String contents,
            List<String> imagePathList,
            LocalDateTime createAt,
            LocalDateTime updateAt
    ) {}

    @Builder
    public record FetchEstimateTalkPostsResponse(
            List<FetchedEstimateTalkPost> estimateTalkPostList,
            int currentPage,
            int totalPage,
            Long totalElements
    ) {
        @Builder
        public record FetchedEstimateTalkPost(
                Long id,
                String phoneNumber,
                String email,
                String title,
                String contents,
                LocalDateTime createAt,
                LocalDateTime updateAt
        ) {}
    }
}
